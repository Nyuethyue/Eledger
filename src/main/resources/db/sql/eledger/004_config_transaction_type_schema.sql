-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_transaction_type
(
    id   bigint  NOT NULL,
    name varchar NOT NULL
);

ALTER TABLE eledger_config.el_transaction_type
    ADD CONSTRAINT pk_transaction_type PRIMARY KEY (id);

ALTER TABLE eledger_config.el_transaction_type
    ADD CONSTRAINT un_transaction_type_name UNIQUE (name);

CREATE SEQUENCE eledger_config.el_transaction_type_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_transaction_type.id;

-----------------------------------------------------------------------------------------------------------------------------


CREATE TABLE eledger_config.el_transaction_type_description
(
    id                  bigint  NOT NULL,
    language_code       varchar NOT NULL,
    value               varchar NULL,
    transaction_type_id integer NOT NULL
);

ALTER TABLE eledger_config.el_transaction_type_description
    ADD CONSTRAINT pk_transaction_type_description PRIMARY KEY (id);

ALTER TABLE eledger_config.el_transaction_type_description
    ADD CONSTRAINT un_transaction_type_dsc_transaction_type_id_lng_code
        UNIQUE (transaction_type_id, language_code);

ALTER TABLE ONLY eledger_config.el_transaction_type_description
    ADD CONSTRAINT fk_transaction_type_dsc_transaction_type
        FOREIGN KEY (transaction_type_id)
            REFERENCES eledger_config.el_transaction_type (id);

CREATE INDEX IF NOT EXISTS fki_transaction_type_dsc_transaction_type
    ON eledger_config.el_transaction_type_description (transaction_type_id);

CREATE SEQUENCE eledger_config.el_transaction_type_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_transaction_type_description.id;

-----------------------------------------------------------------------------------------------------------------------------

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_transaction_type_attribute
(
    id           bigint  NOT NULL,
    name         varchar NOT NULL,
    data_type_id integer NOT NULL
);

ALTER TABLE eledger_config.el_transaction_type_attribute
    ADD CONSTRAINT pk_transaction_type_attribute PRIMARY KEY (id);

ALTER TABLE eledger_config.el_transaction_type_attribute
    ADD CONSTRAINT un_transaction_type_attribute_name UNIQUE (name);

CREATE SEQUENCE eledger_config.el_transaction_type_attribute_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_transaction_type_attribute.id;

-----------------------------------------------------------------------------------------------------------------------------


CREATE TABLE eledger_config.el_transaction_type_attribute_description
(
    id                            bigint  NOT NULL,
    language_code                 varchar NOT NULL,
    value                         varchar NULL,
    transaction_type_attribute_id integer NOT NULL
);

ALTER TABLE eledger_config.el_transaction_type_attribute_description
    ADD CONSTRAINT pk_transaction_type_attribute_description PRIMARY KEY (id);

ALTER TABLE eledger_config.el_transaction_type_attribute_description
    ADD CONSTRAINT un_transaction_type_attr_dsc_transaction_type_attr_id_lng_code
        UNIQUE (transaction_type_attribute_id, language_code);

ALTER TABLE ONLY eledger_config.el_transaction_type_attribute_description
    ADD CONSTRAINT fk_transaction_type_attribute_dsc_transaction_type_attribute
        FOREIGN KEY (transaction_type_attribute_id)
            REFERENCES eledger_config.el_transaction_type_attribute (id);

CREATE INDEX IF NOT EXISTS fki_transaction_type_attribute_dsc_transaction_type_attribute
    ON eledger_config.el_transaction_type_attribute_description (transaction_type_attribute_id);

CREATE SEQUENCE eledger_config.el_transaction_type_attribute_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_transaction_type_attribute_description.id;

-----------------------------------------------------------------------------------------------------------------------------


CREATE TABLE eledger_config.el_transaction_type_transaction_type_attribute
(
    transaction_type_id           bigint NOT NULL,
    transaction_type_attribute_id bigint NOT NULL
);

ALTER TABLE eledger_config.el_transaction_type_transaction_type_attribute
    ADD CONSTRAINT pk_transaction_type_transaction_type_attribute PRIMARY KEY (transaction_type_id, transaction_type_attribute_id);

ALTER TABLE eledger_config.el_transaction_type_transaction_type_attribute
    ADD CONSTRAINT fk_transaction_type_transaction_type_attribute_transaction_type
        FOREIGN KEY (transaction_type_id)
            REFERENCES eledger_config.el_transaction_type (id);

ALTER TABLE eledger_config.el_transaction_type_transaction_type_attribute
    ADD CONSTRAINT fk_transaction_type_transaction_type_attribute_trans_type_attr
        FOREIGN KEY (transaction_type_attribute_id)
            REFERENCES eledger_config.el_transaction_type_attribute (id);

CREATE INDEX IF NOT EXISTS fki_transaction_type_transaction_type_attribute_trans_type
    ON eledger_config.el_transaction_type_transaction_type_attribute (transaction_type_id);

-----------------------------------------------------------------------------------------------------------------------------
