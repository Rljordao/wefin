INSERT INTO currencies (code, name, kingdom, created_at, updated_at) VALUES
('OUR', 'Ouro Real', 'SRM', NOW(), NOW()),
('TBR', 'Tibar', 'Reino dos Anãos', NOW(), NOW());

INSERT INTO exchange_rates (source_currency_id, target_currency_id, rate, effective_date, expiration_date, active, created_at, updated_at)
VALUES
(1, 2, 2.5, NOW(), '9999-12-31', TRUE, NOW(), NOW()),
(2, 1, 0.4, NOW(), '9999-12-31', TRUE, NOW(), NOW());

INSERT INTO products (name, description, category, kingdom, created_at, updated_at) VALUES
('Peles de Lobo', 'Peles de alta qualidade dos lobos do norte', 'Materiais', 'SRM', NOW(), NOW()),
('Hidromel Dourado', 'Bebida fermentada dos deuses', 'Bebidas', 'SRM', NOW(), NOW()),
('Machado de Batalha', 'Arma forjada pelos mestres anões', 'Armas', 'Reino dos Anãos', NOW(), NOW()),
('Cristais Luminosos', 'Cristais raros das montanhas', 'Materiais', 'Reino dos Anãos', NOW(), NOW());


INSERT INTO product_conversion_rules (product_id, source_currency_id, target_currency_id, conversion_formula, effective_date, active, created_at, updated_at)
VALUES
(1, 1, 2, 'amount * baseRate', NOW(), TRUE, NOW(), NOW()),
(2, 1, 2, 'amount * baseRate', NOW(), TRUE, NOW(), NOW()),
(3, 2, 1, 'amount * baseRate', NOW(), TRUE, NOW(), NOW()),
(4, 2, 1, 'amount * baseRate', NOW(), TRUE, NOW(), NOW());
