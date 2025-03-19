-- liquibase formatted sql

-- changeset edkamena:1
CREATE TABLE if NOT EXISTS dishes (
    id UUID NOT NULL,
    title VARCHAR(32) NOT NULL,
    date_time TIMESTAMP(6) NOT NULL,
    users_id UUID NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY (users_id) REFERENCES users(id)
);