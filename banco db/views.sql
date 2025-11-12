--  VIEWS (Visões)

-- VIEW 1: view_quartos_disponiveis
-- Esta view é para o usuário "Recepcionista".
-- Ela simplifica a consulta de quais quartos estão livres
-- e já traz o preço e o tipo do quarto (JOIN com tipos_quarto).

CREATE VIEW `view_quartos_disponiveis` AS
SELECT 
    q.id_quarto,
    q.numero_quarto,
    q.andar,
    tq.nome_tipo,
    tq.capacidade_maxima,
    tq.preco_base_diaria
FROM 
    `quartos` AS q
JOIN 
    `tipos_quarto` AS tq ON q.id_tipo_quarto = tq.id_tipo_quarto
WHERE 
    q.status_quarto = 'Disponível'
ORDER BY 
    q.numero_quarto;

-- VIEW 2: view_detalhes_reservas
-- Esta view é para o usuário "Gerente".
-- Ela une 4 tabelas (`reservas`, `usuarios`, `quartos`, `tipos_quarto`)
-- para criar um relatório legível de todas as reservas,
-- mostrando o nome do cliente, o quarto, as datas e o valor.view_quartos_disponiveisusuariosusuariosview_quartos_disponiveis
CREATE VIEW `view_detalhes_reservas` AS
SELECT 
    r.id_reserva,
    r.status_reserva,
    u.nome AS nome_cliente,
    u.email AS email_cliente,
    q.numero_quarto,
    tq.nome_tipo AS tipo_quarto,
    r.data_checkin,
    r.data_checkout,
    DATEDIFF(r.data_checkout, r.data_checkin) AS total_noites,
    r.valor_total_reserva,
    r.data_reserva
FROM 
    `reservas` AS r
JOIN 
    `usuarios` AS u ON r.id_cliente = u.id_usuario
JOIN 
    `quartos` AS q ON r.id_quarto = q.id_quarto
JOIN 
    `tipos_quarto` AS tq ON q.id_tipo_quarto = tq.id_tipo_quarto
ORDER BY 
    r.data_checkin DESC;

-- SCRIPT 03: VIEWS (Visões)

CREATE OR REPLACE VIEW `view_quartos_disponiveis` AS
SELECT 
    q.id_quarto, q.numero_quarto, q.andar,
    tq.nome_tipo, tq.capacidade_maxima, tq.preco_base_diaria
FROM `quartos` AS q
JOIN `tipos_quarto` AS tq ON q.id_tipo_quarto = tq.id_tipo_quarto
WHERE q.status_quarto = 'Disponível'
ORDER BY q.numero_quarto;

CREATE OR REPLACE VIEW `view_detalhes_reservas` AS
SELECT 
    r.id_reserva, r.status_reserva,
    u.nome AS nome_cliente, u.email AS email_cliente,
    q.numero_quarto, tq.nome_tipo AS tipo_quarto,
    r.data_checkin, r.data_checkout,
    DATEDIFF(r.data_checkout, r.data_checkin) AS total_noites,
    r.valor_total_reserva, r.data_reserva
FROM `reservas` AS r
JOIN `usuarios` AS u ON r.id_cliente = u.id_usuario
JOIN `quartos` AS q ON r.id_quarto = q.id_quarto
JOIN `tipos_quarto` AS tq ON q.id_tipo_quarto = tq.id_tipo_quarto
ORDER BY r.data_checkin DESC;