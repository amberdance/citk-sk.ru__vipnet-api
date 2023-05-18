CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    created_at TIMESTAMP NULL DEFAULT NULL,
    updated_at TIMESTAMP NULL DEFAULT NULL,
    username      VARCHAR(64)   NOT NULL UNIQUE,
    password   VARCHAR(2048) NOT NULL,
    first_name VARCHAR(64) NOT NULL,
    last_name  VARCHAR(64) DEFAULT NULL,
    patronymic VARCHAR(64) DEFAULT NULL,
    enabled    BOOLEAN       NOT NULL DEFAULT FALSE,
    role       VARCHAR(32)   NOT NULL
)
