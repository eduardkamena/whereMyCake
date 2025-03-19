-- liquibase formatted sql

-- changeset edkamena:1
CREATE TABLE if NOT EXISTS users (
    id UUID NOT NULL,
    name VARCHAR(32) NOT NULL,
    email VARCHAR(32) UNIQUE NOT NULL,
    gender VARCHAR(1) NOT NULL,
    age INTEGER NOT NULL,
    weight REAL NOT NULL,
    height INTEGER NOT NULL,
    aim VARCHAR(12) NOT NULL,

    PRIMARY KEY(id)
);