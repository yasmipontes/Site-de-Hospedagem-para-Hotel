-- Tabela 1: grupos_usuarios
-- Armazena os "papéis" (Roles) do sistema para controle de acesso.

CREATE TABLE IF NOT EXISTS `grupos_usuarios` (
  `id_grupo` INT NOT NULL AUTO_INCREMENT,
  `nome_grupo` VARCHAR(50) NOT NULL UNIQUE, -- 'UNIQUE' garante que não teremos dois grupos "Gerente"
  `descricao` TEXT NULL,
  PRIMARY KEY (`id_grupo`)
) 
ENGINE = InnoDB
COMMENT = 'Armazena os papéis de usuário (ex: Cliente, Gerente, Recepcionista).';

-- Tabela 2: usuarios
-- Armazena clientes e funcionários. A coluna `id_grupo` faz a ligação
-- com a tabela `grupos_usuarios`, definindo a permissão do usuário.

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `id_grupo` INT NOT NULL, -- Chave estrangeira que define o papel do usuário.
  `nome` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE, -- 'UNIQUE' é crucial para o login (não podem existir dois e-mails iguais).
  `senha_hash` VARCHAR(255) NOT NULL, -- 'senha_hash' pois nunca se armazena senha em texto puro.
  `telefone` VARCHAR(20) NULL, -- 'NULL' pois nem todo usuário pode querer fornecer telefone.
  `data_criacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 'DEFAULT CURRENT_TIMESTAMP' para fins de auditoria (sabemos quando o usuário se cadastrou).
  PRIMARY KEY (`id_usuario`),
  
  -- Definição da Chave Estrangeira (FK)
  CONSTRAINT `fk_usuarios_grupos`
    FOREIGN KEY (`id_grupo`)
    REFERENCES `grupos_usuarios` (`id_grupo`)
    
    -- ON DELETE RESTRICT: IMPEDE que um `grupo_usuario` (ex: "Cliente") seja deletado se houver `usuarios` ligados a ele. 
    ON DELETE RESTRICT 
    -- ON UPDATE CASCADE: Se o `id_grupo` (PK) for alterado, atualiza automaticamente o `id_grupo` (FK) em `usuarios`. 
    ON UPDATE CASCADE 
) 
ENGINE = InnoDB
COMMENT = 'Tabela central de usuários, clientes e funcionários.';


-- Tabela 3: tipos_quarto
-- Separa as *categorias* dos *quartos físicos*.
-- Permite que o Gerente mude o preço da "Suíte Deluxe" uma vez,
-- e todos os quartos desse tipo são afetados.
CREATE TABLE IF NOT EXISTS `tipos_quarto` (
  `id_tipo_quarto` INT NOT NULL AUTO_INCREMENT,
  `nome_tipo` VARCHAR(100) NOT NULL,
  `descricao` TEXT NULL,
  `capacidade_maxima` INT NOT NULL DEFAULT 1,
  `preco_base_diaria` DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (`id_tipo_quarto`)
) 
ENGINE = InnoDB
COMMENT = 'Categorias dos quartos (ex: Standard, Deluxe, Suíte).';

-- Tabela 4: quartos
-- Armazena os quartos físicos, reais, do hotel.
-- Cada quarto pertence a um `tipo_quarto`.
CREATE TABLE IF NOT EXISTS `quartos` (
  `id_quarto` INT NOT NULL AUTO_INCREMENT,
  `id_tipo_quarto` INT NOT NULL, -- FK para saber a categoria (e o preço) do quarto.
  `numero_quarto` VARCHAR(10) NOT NULL UNIQUE, -- 'UNIQUE' para garantir que não haja dois quartos "101".
  `andar` INT NULL,
  `status_quarto` ENUM('Disponível', 'Ocupado', 'Manutenção', 'Limpeza') NOT NULL DEFAULT 'Disponível', 
  PRIMARY KEY (`id_quarto`),
  
  CONSTRAINT `fk_quartos_tipos`
    FOREIGN KEY (`id_tipo_quarto`)
    REFERENCES `tipos_quarto` (`id_tipo_quarto`)
    ON DELETE RESTRICT -- Não deixa deletar um "Tipo de Quarto" (ex: "Standard") se houver quartos reais cadastrados como "Standard".
    ON UPDATE CASCADE
) 
ENGINE = InnoDB
COMMENT = 'Os quartos físicos e individuais do hotel.';

-- Tabela 5: servicos_adicionais
-- Catálogo de serviços (Frigobar, Spa, etc.)

CREATE TABLE IF NOT EXISTS `servicos_adicionais` (
  `id_servico` INT NOT NULL AUTO_INCREMENT,
  `nome_servico` VARCHAR(100) NOT NULL UNIQUE, -- 'UNIQUE' para não duplicar serviços.
  `descricao` TEXT NULL,
  `preco_unitario` DECIMAL(10, 2) NOT NULL, -- 'DECIMAL' para dinheiro.
  PRIMARY KEY (`id_servico`)
) 
ENGINE = InnoDB
COMMENT = 'Catálogo de serviços extras (frigobar, lavanderia, spa).';

-- Tabela 6: reservas
-- Registra a transação principal (a estadia).
CREATE TABLE IF NOT EXISTS `reservas` (
  -- obs: O ID da reserva será alterado. O requisito pede uma FUNÇÃO
  -- de geração de ID para dados críticos. usamos isso na Parte 4.
  -- Por enquanto, usamos AUTO_INCREMENT para construir a tabela.
  `id_reserva` INT NOT NULL AUTO_INCREMENT,
  `id_cliente` INT NOT NULL, -- FK para `usuarios`. quem reservou.
  `id_quarto` INT NOT NULL, -- FK para `quartos`. qual quarto foi reservado.
  `data_checkin` DATE NOT NULL,
  `data_checkout` DATE NOT NULL,
  `status_reserva` ENUM('Pendente', 'Confirmada', 'Cancelada', 'CheckIn', 'CheckOut') NOT NULL DEFAULT 'Pendente',
  `valor_total_reserva` DECIMAL(10, 2) NOT NULL DEFAULT 0.00, -- Valor total (diárias + serviços). Será atualizado por Triggers.
  `data_reserva` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Auditoria: quando o cliente fez a reserva.
  PRIMARY KEY (`id_reserva`),
  
  CONSTRAINT `fk_reservas_cliente`
    FOREIGN KEY (`id_cliente`)
    REFERENCES `usuarios` (`id_usuario`)
    ON DELETE RESTRICT -- Não deixa deletar um `usuario` (cliente) se ele tiver `reservas` no histórico.
    ON UPDATE CASCADE,
    
  CONSTRAINT `fk_reservas_quarto`
    FOREIGN KEY (`id_quarto`)
    REFERENCES `quartos` (`id_quarto`)
    ON DELETE RESTRICT -- Não deixa deletar um `quarto` se ele tiver `reservas`.
    ON UPDATE CASCADE,
    
  -- CONSTRAINT (Regra de Negócio): Garante que a data de checkout
  -- seja sempre POSTERIOR à data de check-in.
  CONSTRAINT `chk_datas_reserva` CHECK (`data_checkout` > `data_checkin`)
) 
ENGINE = InnoDB
COMMENT = 'Tabela principal que armazena as estadias dos clientes.';

-- Tabela 7: reservas_servicos (Tabela Associativa N:M)
-- entre `reservas` e `servicos_adicionais`.
-- Uma reserva (N) pode ter vários serviços (M).
CREATE TABLE IF NOT EXISTS `reservas_servicos` (
  `id_reserva_servico` INT NOT NULL AUTO_INCREMENT,
  `id_reserva` INT NOT NULL, -- FK para `reservas`
  `id_servico` INT NOT NULL, -- FK para `servicos_adicionais`
  `quantidade` INT NOT NULL DEFAULT 1,
  
  --  Se o Gerente mudar o preço do Frigobar amanhã,
  -- o valor cobrado do cliente (que consumiu ontem) é preservado aqui.
  `preco_cobrado` DECIMAL(10, 2) NOT NULL, 
  `data_consumo` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_reserva_servico`),
  
  CONSTRAINT `fk_reservas_servicos_reserva`
    FOREIGN KEY (`id_reserva`)
    REFERENCES `reservas` (`id_reserva`)
    -- ON DELETE CASCADE: Se a `reserva` for deletada, os consumos (serviços)
    -- associados a ela são deletados juntos. O consumo não existe sem a reserva.
    ON DELETE CASCADE 
    ON UPDATE CASCADE,
    
  CONSTRAINT `fk_reservas_servicos_servico`
    FOREIGN KEY (`id_servico`)
    REFERENCES `servicos_adicionais` (`id_servico`)
    -- ON DELETE RESTRICT: Protege o histórico.

    ON DELETE RESTRICT
    ON UPDATE CASCADE
) 
ENGINE = InnoDB
COMMENT = 'Tabela N:M. Registra os serviços consumidos em uma reserva.';

-- Tabela 8: pagamentos
-- Armazena o histórico de pagamentos.
-- Uma reserva pode ter múltiplos pagamentos.
CREATE TABLE IF NOT EXISTS `pagamentos` (
  
  `id_pagamento` INT NOT NULL AUTO_INCREMENT,
  `id_reserva` INT NOT NULL,
  `metodo_pagamento` ENUM('Cartão de Crédito', 'PIX', 'Dinheiro', 'Transferência') NULL,
  `valor_pago` DECIMAL(10, 2) NOT NULL,
  `data_pagamento` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  `status_pagamento` ENUM('Pendente', 'Aprovado', 'Rejeitado') NOT NULL DEFAULT 'Pendente',
  PRIMARY KEY (`id_pagamento`),
  
  CONSTRAINT `fk_pagamentos_reserva`
    FOREIGN KEY (`id_reserva`)
    REFERENCES `reservas` (`id_reserva`)
    -- ON DELETE RESTRICT: Proteção financeira.
    -- Não permite deletar uma `reserva` se existirem `pagamentos`
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) 
ENGINE = InnoDB
COMMENT = 'Histórico de transações financeiras (pagamentos) de cada reserva.';