-- INSERIR OS GRUPOS DE USUÁRIOS
-- (Isto é parte do script 05_usuarios_permissoes.sql)

INSERT INTO `grupos_usuarios` (id_grupo, nome_grupo, descricao)
VALUES 
(1, 'Gerente', 'Acesso total ao sistema, relatórios e configurações.'),
(2, 'Recepcionista', 'Acesso a check-in, check-out e gerenciamento de reservas.'),
(3, 'Cliente', 'Acesso limitado para ver e fazer suas próprias reservas.')
ON DUPLICATE KEY UPDATE nome_grupo = VALUES(nome_grupo);

SELECT 'Grupos de usuários inseridos com sucesso!' AS status;