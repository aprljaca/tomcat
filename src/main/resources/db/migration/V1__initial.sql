CREATE TABLE IF NOT EXISTS "users" (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS "reset_tokens" (
    id SERIAL PRIMARY KEY,
    token VARCHAR(36) NOT NULL,
    expiration_time TIMESTAMPTZ NOT NULL,
    user_id SERIAL UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS "posts" (
    id SERIAL PRIMARY KEY,
    text VARCHAR(300) NOT NULL,
    created_time TIMESTAMPTZ NOT NULL,
    user_id SERIAL NOT NULL
);

CREATE TABLE IF NOT EXISTS "comments" (
    id SERIAL PRIMARY KEY,
    post_id SERIAL NOT NULL,
    user_id SERIAL NOT NULL,
    text VARCHAR(300) NOT NULL,
    created_time TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS "likes" (
    id SERIAL PRIMARY KEY,
    post_id SERIAL NOT NULL,
    user_id SERIAL NOT NULL,
    created_time TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS "profil_images" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    image_path VARCHAR(300) NOT NULL,
    type VARCHAR(100) NOT NULL,
    user_id SERIAL UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS "follows" (
    id SERIAL PRIMARY KEY,
    follower_id SERIAL NOT NULL,
    following_id SERIAL NOT NULL
);

CREATE TABLE IF NOT EXISTS "notifications" (
    id SERIAL PRIMARY KEY,
    user_id SERIAL NOT NULL,
    unread_notification SERIAL NOT NULL,
    unread_follow SERIAL NOT NULL,
    unread_comment SERIAL NOT NULL,
    unread_like SERIAL NOT NULL
);