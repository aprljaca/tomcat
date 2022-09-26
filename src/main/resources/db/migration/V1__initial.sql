CREATE TABLE IF NOT EXISTS "users" (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS "reset_token" (
    id SERIAL PRIMARY KEY,
    token VARCHAR(36) NOT NULL,
    expiration_time TIMESTAMPTZ NOT NULL,
    user_id SERIAL UNIQUE NOT NULL
);