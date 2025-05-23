
CREATE SCHEMA IF NOT EXISTS market;

ALTER ROLE market_user SET search_path TO market, public;

ALTER USER market_user SET search_path TO market, public;