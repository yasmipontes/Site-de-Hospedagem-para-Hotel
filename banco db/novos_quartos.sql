-- -----------------------------------------------------------------
-- SCRIPT 06 (COMPLETO): Inserir Dados de Exemplo
-- -----------------------------------------------------------------

-- 1. Inserir Tipos de Quarto (Os "Pais")
INSERT INTO `tipos_quarto` (id_tipo_quarto, nome_tipo, descricao, capacidade_maxima, preco_base_diaria)
VALUES 
(1, 'Standard', 'Quarto padrão com cama de casal.', 2, 150.00),
(2, 'Deluxe', 'Quarto maior com cama king size e vista.', 2, 250.00),
(3, 'Suíte Família', 'Quarto com duas camas de casal e mini-cozinha.', 4, 380.00)
ON DUPLICATE KEY UPDATE nome_tipo = VALUES(nome_tipo); -- Não faz nada se já existir

-- 2. Inserir Quartos Físicos (Os "Filhos")
-- (Inserindo os 10 quartos de uma vez)
INSERT INTO `quartos` (id_tipo_quarto, numero_quarto, andar, status_quarto)
VALUES
(1, '101', 1, 'Disponível'), 
(1, '102', 1, 'Disponível'),
(2, '201', 2, 'Manutenção'), -- Este não vai aparecer no site
(3, '301', 3, 'Disponível'),
(1, '103', 1, 'Disponível'),
(1, '104', 1, 'Disponível'),
(2, '202', 2, 'Disponível'),
(2, '203', 2, 'Disponível'),
(2, '204', 2, 'Disponível'),
(3, '302', 3, 'Disponível'),
(3, '303', 3, 'Disponível')
ON DUPLICATE KEY UPDATE numero_quarto = VALUES(numero_quarto);

SELECT 'Dados de exemplo (Tipos e Quartos) inseridos!' AS status;