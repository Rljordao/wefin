CREATE TABLE currencies (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
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