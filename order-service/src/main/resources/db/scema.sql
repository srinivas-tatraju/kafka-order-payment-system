CREATE TABLE IF NOT EXISTS orders
(
    id      BIGSERIAL PRIMARY KEY,
    product VARCHAR(255),
    amount  DOUBLE PRECISION
);


CREATE TABLE IF NOT EXISTS processed_events
(
    order_id     BIGINT PRIMARY KEY,
    processed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE outbox_events
(
    id         BIGSERIAL PRIMARY KEY,
    event_type VARCHAR(255),
    payload    TEXT,
    status     VARCHAR(20) DEFAULT 'NEW',
    created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);