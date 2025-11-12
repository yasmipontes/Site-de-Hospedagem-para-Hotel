-- USUÁRIOS E CONTROLE DE ACESSO
-- Criar usuários com diferentes níveis de acesso.

-- Popular os 'grupos_usuarios' (Nossos Papéis)
-- Estes são os dados que o backend vai ler para saber
-- se um usuário é "Gerente" ou "Cliente".
-- (Usamos ON DUPLICATE KEY para poder rodar o script várias vezes)
INSERT INTO `grupos_usuarios` (id_grupo, nome_grupo, descricao)
VALUES (1, 'Gerente', 'Acesso total ao sistema, relatórios e configurações.')
ON DUPLICATE KEY UPDATE nome_grupo = VALUES(nome_grupo);

INSERT INTO `grupos_usuarios` (id_grupo, nome_grupo, descricao)
VALUES (2, 'Recepcionista', 'Acesso a check-in, check-out e gerenciamento de reservas.')
ON DUPLICATE KEY UPDATE nome_grupo = VALUES(nome_grupo);

INSERT INTO `grupos_usuarios` (id_grupo, nome_grupo, descricao)
VALUES (3, 'Cliente', 'Acesso limitado para ver e fazer suas próprias reservas.')
ON DUPLICATE KEY UPDATE nome_grupo = VALUES(nome_grupo);


--  Criar os usuários do BANCO DE DADOS
-- Estes são os usuários que o BACKEND usará para
-- se conectar ao MySQL.
-- (Altere 'sua_senha_segura' para uma senha forte)

-- Usuário 1: O Gerente (Mais privilégios)
CREATE USER IF NOT EXISTS 'gerente_hotel'@'localhost' 
IDENTIFIED BY 'sua_senha_segura_gerente';

-- Usuário 2: O Recepcionista (Médios privilégios)
CREATE USER IF NOT EXISTS 'recepcionista_hotel'@'localhost' 
IDENTIFIED BY 'sua_senha_segura_recep';

-- Usuário 3: O Cliente (Privilégios Mínimos)
-- Este será o usuário padrão do backend para operações públicas.
CREATE USER IF NOT EXISTS 'cliente_app'@'localhost' 
IDENTIFIED BY 'sua_senha_segura_cliente';

--  Conceder Permissões (GRANT)

-- Permissões do Gerente:
-- Pode ver tudo (SELECT), executar tudo (EXECUTE) e ver as views.
GRANT SELECT ON `hotel_db`.* TO 'gerente_hotel'@'localhost'; -- Acesso a todas as tabelas e views
GRANT EXECUTE ON PROCEDURE `hotel_db`.`sp_fazer_reserva` TO 'gerente_hotel'@'localhost';
GRANT EXECUTE ON PROCEDURE `hotel_db`.`sp_adicionar_servico_reserva` TO 'gerente_hotel'@'localhost';


-- Permissões do Recepcionista:
-- NÃO PODE ver as tabelas direto.
-- SÓ PODE executar as procedures e ver as views.
-- Este é o usuário ideal para a maior parte da operação.
GRANT EXECUTE ON PROCEDURE `hotel_db`.`sp_fazer_reserva` TO 'recepcionista_hotel'@'localhost';
GRANT EXECUTE ON PROCEDURE `hotel_db`.`sp_adicionar_servico_reserva` TO 'recepcionista_hotel'@'localhost';
GRANT SELECT ON `hotel_db`.`view_quartos_disponiveis` TO 'recepcionista_hotel'@'localhost';
GRANT SELECT ON `hotel_db`.`view_detalhes_reservas` TO 'recepcionista_hotel'@'localhost';
-- Permissão para logar usuários (ler a tabela de usuários)
GRANT SELECT (id_usuario, email, senha_hash, id_grupo) ON `hotel_db`.`usuarios` TO 'recepcionista_hotel'@'localhost';


-- Permissões do Cliente:
-- O usuário mais restrito.
-- Só pode executar a procedure de fazer reserva e se cadastrar.
GRANT EXECUTE ON PROCEDURE `hotel_db`.`sp_fazer_reserva` TO 'cliente_app'@'localhost';
-- Permissão para se cadastrar (INSERT na tabela usuarios)
GRANT INSERT (nome, email, senha_hash, id_grupo) ON `hotel_db`.`usuarios` TO 'cliente_app'@'localhost';
-- Permissão para fazer login (SELECT na tabela usuarios)
GRANT SELECT (id_usuario, email, senha_hash, id_grupo) ON `hotel_db`.`usuarios` TO 'cliente_app'@'localhost';


-- Informa ao MySQL para APLICAR as permissões que acabamos de dar.
FLUSH PRIVILEGES;