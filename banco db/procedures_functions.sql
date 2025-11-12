-- PROCEDURES & FUNCTIONS (Lógica de Negócio)

SET GLOBAL log_bin_trust_function_creators = 1;

-- FUNCTION 2/2: fn_calcular_total_estadia
-- Esta função calcula o valor base da estadia (preço diária * noites).

DELIMITER //
CREATE FUNCTION IF NOT EXISTS `fn_calcular_total_estadia`(
    p_id_quarto INT,
    p_data_checkin DATE,
    p_data_checkout DATE
) 
RETURNS DECIMAL(10, 2)
DETERMINISTIC
READS SQL DATA
BEGIN
    DECLARE v_preco_diaria DECIMAL(10, 2);
    DECLARE v_total_noites INT;
    DECLARE v_valor_total DECIMAL(10, 2);

    -- 1. Buscar o preço da diária do quarto
    SELECT tq.preco_base_diaria INTO v_preco_diaria
    FROM quartos q
    JOIN tipos_quarto tq ON q.id_tipo_quarto = tq.id_tipo_quarto
    WHERE q.id_quarto = p_id_quarto;

    -- 2. Calcular o número de noites
    SET v_total_noites = DATEDIFF(p_data_checkout, p_data_checkin);

    -- 3. Calcular o total
    SET v_valor_total = v_preco_diaria * v_total_noites;

    RETURN v_valor_total;
END //
DELIMITER ;

-- PROCEDURE 1/2: sp_fazer_reserva
-- Encapsula a lógica de negócios complexa de criar uma reserva.

DELIMITER //
CREATE PROCEDURE `sp_fazer_reserva`(
    IN p_id_cliente INT,
    IN p_id_quarto INT,
    IN p_data_checkin DATE,
    IN p_data_checkout DATE
)
BEGIN
    DECLARE v_valor_calculado DECIMAL(10, 2);
    DECLARE v_conflitos INT;

    -- 1. Verifica se há conflito de datas para o MESMO quarto
    SELECT COUNT(id_reserva) INTO v_conflitos
    FROM reservas
    WHERE id_quarto = p_id_quarto
      AND status_reserva IN ('Confirmada', 'Pendente', 'CheckIn')
      AND (
          (p_data_checkin BETWEEN data_checkin AND data_checkout) OR
          (p_data_checkout BETWEEN data_checkin AND data_checkout) OR
          (data_checkin BETWEEN p_data_checkin AND p_data_checkout)
      );

    -- 2. Se não houver conflitos (v_conflitos = 0), insere a reserva
    IF v_conflitos = 0 THEN
        -- Calcula o valor usando nossa nova Função
        SET v_valor_calculado = fn_calcular_total_estadia(p_id_quarto, p_data_checkin, p_data_checkout);

        -- Insere a reserva.
        -- O 'id_reserva' será gerado automaticamente pelo TRIGGER
        INSERT INTO reservas (id_cliente, id_quarto, data_checkin, data_checkout, status_reserva, valor_total_reserva)
        VALUES (p_id_cliente, p_id_quarto, p_data_checkin, p_data_checkout, 'Pendente', v_valor_calculado);
        
        -- Retorna uma mensagem de sucesso (o backend vai ler isso)
        SELECT 'Reserva criada com sucesso.' AS mensagem;
    ELSE
        -- Retorna uma mensagem de erro (o backend vai ler isso)
        SELECT 'ERRO: O quarto não está disponível para estas datas.' AS mensagem;
    END IF;

END //
DELIMITER ;

-- PROCEDURE 2/2: sp_adicionar_servico_reserva
-- Encapsula a lógica de adicionar um consumo (ex: Frigobar)
-- a uma reserva existente.
DELIMITER //
CREATE PROCEDURE `sp_adicionar_servico_reserva`(
    IN p_id_reserva INT,
    IN p_id_servico INT,
    IN p_quantidade INT
)
BEGIN
    DECLARE v_preco_servico DECIMAL(10, 2);
    DECLARE v_valor_servico_total DECIMAL(10, 2);

    -- 1. Pega o preço unitário ATUAL do serviço
    SELECT preco_unitario INTO v_preco_servico
    FROM servicos_adicionais
    WHERE id_servico = p_id_servico;

    -- 2. Calcula o valor total do consumo (quantidade * preço)
    SET v_valor_servico_total = v_preco_servico * p_quantidade;

    -- 3. Insere o consumo na tabela N:M 'reservas_servicos'
    -- (Isso "congela" o preço cobrado no momento do consumo)
    INSERT INTO reservas_servicos (id_reserva, id_servico, quantidade, preco_cobrado)
    VALUES (p_id_reserva, p_id_servico, p_quantidade, v_valor_servico_total);

    -- 4. ATUALIZA o valor total na tabela 'reservas'
    UPDATE reservas
    SET valor_total_reserva = valor_total_reserva + v_valor_servico_total
    WHERE id_reserva = p_id_reserva;
    
    SELECT 'Serviço adicionado com sucesso.' AS mensagem;

END //
DELIMITER ;