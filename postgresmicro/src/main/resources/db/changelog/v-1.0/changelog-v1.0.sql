--liquibase formatted sql

--changeset vitaliy:1-1
CREATE TABLE audits
(
    id      bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    message varchar(500),
    CONSTRAINT audits_pkey PRIMARY KEY (id)
);

--changeset vitaliy:1-2
CREATE TABLE users
(
    id   bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    name varchar(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

--changeset vitaliy:1-3
CREATE TABLE visas
(
    id      bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    country character varying(255),
    user_id bigint,
    CONSTRAINT PK_Visas PRIMARY KEY (id),
    CONSTRAINT FK_Visas FOREIGN KEY (user_id)
        REFERENCES users (id)
);
