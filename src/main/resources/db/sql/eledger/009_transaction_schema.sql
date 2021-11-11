-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger.el_transaction
(
    id                  bigint         NOT NULL,
    drn                 varchar        NOT NULL,
    taxpayer_id         bigint         NOT NULL,
    gl_account_code     varchar        NOT NULL,
    settlement_date     date           NOT NULL,
    amount              numeric(20, 2) NOT NULL,
    creation_date_time  timestamp      NOT NULL,
    transaction_type_id bigint         NOT NULL
);

ALTER TABLE eledger.el_transaction
    ADD CONSTRAINT pk_transaction
        PRIMARY KEY (id);

ALTER TABLE eledger.el_transaction
    ADD CONSTRAINT fk_transaction_taxpayer
        FOREIGN KEY (taxpayer_id)
            REFERENCES eledger.el_taxpayer (id);

ALTER TABLE eledger.el_transaction
    ADD CONSTRAINT fk_transaction_transaction_type
        FOREIGN KEY (transaction_type_id)
            REFERENCES eledger_config.transaction_type (id);

CREATE INDEX IF NOT EXISTS fki_transaction_taxpayer
    ON eledger.el_transaction (taxpayer_id);

CREATE SEQUENCE eledger.el_transaction_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger.el_transaction.id;

-----------------------------------------------------------------------------------------------------------------------------


-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger.el_transaction_attribute
(
    id                            bigint  NOT NULL,
    transaction_type_attribute_id bigint  NOT NULL,
    value                         varchar NOT NULL,
    transaction_id                bigint  NOT NULL
);

ALTER TABLE eledger.el_transaction_attribute
    ADD CONSTRAINT pk_transaction_attribute
        PRIMARY KEY (id);

ALTER TABLE eledger.el_transaction_attribute
    ADD CONSTRAINT fk_transaction_attribute_transaction_type_attribute
        FOREIGN KEY (transaction_type_attribute_id)
            REFERENCES eledger_config.transaction_type_attribute (id);

ALTER TABLE eledger.el_transaction_attribute
    ADD CONSTRAINT fk_transaction_attribute_transaction
        FOREIGN KEY (transaction_id)
            REFERENCES eledger.el_transaction (id);

CREATE INDEX IF NOT EXISTS fki_transaction_attribute_transaction_type_attribute
    ON eledger.el_transaction_attribute (transaction_type_attribute_id);

CREATE INDEX IF NOT EXISTS fki_transaction_attribute_transaction
    ON eledger.el_transaction_attribute (transaction_id);

CREATE SEQUENCE eledger.el_transaction_attribute_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger.el_transaction_attribute.id;
