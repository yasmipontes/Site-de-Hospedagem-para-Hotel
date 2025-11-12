-- SCRIPT 02 (v2.3): GERAÇÃO DE IDs CRÍTICOS E TRIGGERS INICIAIS
-- Correção (Erro 1064): Removendo 'IF EXISTS' dos comandos DROP FK

-- Dropar as Chaves Estrangeiras (Sem 'IF EXISTS')
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE `hotel_db`.`reservas_servicos` 
DROP FOREIGN KEY `fk_reservas_servicos_reserva`;

ALTER TABLE `hotel_db`.`pagamentos` 
DROP FOREIGN KEY `fk_pagamentos_reserva`;


-- PASSO B: Alterar as tabelas 
ALTER TABLE `hotel_db`.`reservas` 
MODIFY COLUMN `id_reserva` INT NOT NULL;

ALTER TABLE `hotel_db`.`pagamentos` 
MODIFY COLUMN `id_pagamento` INT NOT NULL;

--  Recriar as Chaves Estrangeiras

ALTER TABLE `hotel_db`.`reservas_servicos` 
ADD CONSTRAINT `fk_reservas_servicos_reserva`
  FOREIGN KEY (`id_reserva`)
  REFERENCES `hotel_db`.`reservas` (`id_reserva`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;

ALTER TABLE `hotel_db`.`pagamentos` 
ADD CONSTRAINT `fk_pagamentos_reserva`
  FOREIGN KEY (`id_reserva`)
  REFERENCES `hotel_db`.`reservas` (`id_reserva`)
  ON DELETE RESTRICT
  ON UPDATE CASCADE;

SET FOREIGN_KEY_CHECKS = 1;

-- Criar a tabela 'sequencias' (Se não existir)
-- -----------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `sequencias` (
  `nome_sequencia` VARCHAR(50) NOT NULL,
  `proximo_valor` BIGINT NOT NULL,
  PRIMARY KEY (`nome_sequencia`)
) ENGINE = InnoDB;

-- -----------------------------------------------------------------
-- PASSO E: Popular os contadores
-- -----------------------------------------------------------------
INSERT INTO `sequencias` (nome_sequencia, proximo_valor) 
VALUES ('seq_reserva', 10001)
ON DUPLICATE KEY UPDATE proximo_valor = proximo_valor;

INSERT INTO `sequencias` (nome_sequencia, proximo_valor) 
VALUES ('seq_pagamento', 50001)
ON DUPLICATE KEY UPDATE proximo_valor = proximo_valor;

-- -----------------------------------------------------------------
-- PASSO F: Criar Função e Triggers (Se não existirem)
-- -----------------------------------------------------------------
SET GLOBAL log_bin_trust_function_creators = 1;

DELIMITER //
CREATE FUNCTION IF NOT EXISTS `fn_get_next_id`(
    p_nome_sequencia VARCHAR(50)
) 
RETURNS BIGINT
DETERMINISTIC
MODIFIES SQL DATA
BEGIN
    UPDATE `sequencias`
    SET `proximo_valor` = LAST_INSERT_ID(`proximo_valor` + 1)
    WHERE `nome_sequencia` = p_nome_sequencia;
    
    RETURN LAST_INSERT_ID();
END//
DELIMITER ;

DROP TRIGGER IF EXISTS `trg_before_insert_reservas`;
DELIMITER //
CREATE TRIGGER `trg_before_insert_reservas`
BEFORE INSERT ON `reservas`
FOR EACH ROW
BEGIN
    SET NEW.id_reserva = fn_get_next_id('seq_reserva');
END//
DELIMITER ;

DROP TRIGGER IF EXISTS `trg_before_insert_pagamentos`;
DELIMITER //
CREATE TRIGGER `trg_before_insert_pagamentos`
BEFORE INSERT ON `pagamentos`
FOR EACH ROW
BEGIN
    SET NEW.id_pagamento = fn_get_next_id('seq_pagamento');
END//
DELIMITER ;