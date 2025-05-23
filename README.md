# Mercado das Mil Sabedorias API

Uma API para gerenciamento de câmbio de moedas entre diferentes reinos, permitindo conversões personalizadas por produto através de fórmulas dinâmicas.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.2.x
- PostgreSQL
- Maven
- Docker (opcional)

## Pré-requisitos

- JDK 21
- Maven 3.8+
- PostgreSQL 14+

## Configuração do Banco de Dados

### Esquema do Banco

Execute os seguintes scripts SQL para criar o esquema e dados iniciais:

```sql

CREATE SCHEMA IF NOT EXISTS market;
ALTER ROLE market_user SET search_path TO market, public;
ALTER USER market_user SET search_path TO market, public;

CREATE TABLE currencies (
    id BIGINT PRIMARY KEY GENERATED ALWAYS ASCREATE SCHEMA IF NOT EXISTS market;

ALTER ROLE market_user SET search_path TO market, public;

ALTER USER market_user SET search_path TO market, public; IDENTITY,
    code VARCHAR(10) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    kingdom VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE exchange_rates (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    source_currency_id BIGINT NOT NULL REFERENCES currencies(id),
    target_currency_id BIGINT NOT NULL REFERENCES currencies(id),
    rate DECIMAL(19,6) NOT NULL,
    effective_date TIMESTAMP NOT NULL,
    expiration_date TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_exchange_rates_active ON exchange_rates(active);
CREATE INDEX idx_exchange_rates_currencies ON exchange_rates(source_currency_id, target_currency_id);

CREATE TABLE products (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(50) NOT NULL,
    kingdom VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE product_conversion_rules (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    product_id BIGINT NOT NULL REFERENCES products(id),
    source_currency_id BIGINT NOT NULL REFERENCES currencies(id),
    target_currency_id BIGINT NOT NULL REFERENCES currencies(id),
    conversion_formula VARCHAR(200) NOT NULL,
    effective_date TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_product_conversion_rules_product ON product_conversion_rules(product_id);
CREATE INDEX idx_product_conversion_rules_currencies ON product_conversion_rules(source_currency_id, target_currency_id);
CREATE INDEX idx_product_conversion_rules_active ON product_conversion_rules(active);

CREATE TABLE transactions (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    product_id BIGINT REFERENCES products(id),
    source_amount DECIMAL(19,6) NOT NULL,
    source_currency_id BIGINT NOT NULL REFERENCES currencies(id),
    target_amount DECIMAL(19,6),
    target_currency_id BIGINT NOT NULL REFERENCES currencies(id),
    applied_rate DECIMAL(19,6),
    transaction_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_transactions_currencies ON transactions(source_currency_id, target_currency_id);
CREATE INDEX idx_transactions_product ON transactions(product_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_transactions_status ON transactions(status);


###  Dados de Exemplo

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