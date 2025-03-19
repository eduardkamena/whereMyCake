-- liquibase formatted sql

-- changeset edkamena:1
CREATE TABLE if NOT EXISTS dishes_parameters (
    id UUID NOT NULL,
    calorie REAL NOT NULL,
    protein REAL NOT NULL,
    fat REAL NOT NULL,
    carb REAL NOT NULL,
    dishes_id UUID NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY (dishes_id) REFERENCES dishes(id)
);