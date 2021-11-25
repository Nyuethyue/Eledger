-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_transaction_type_gl_account
(
    id                  bigint  NOT NULL,
    transaction_type_id bigint  NOT NULL,
    gl_account_id       bigint  NOT NULL,
    account_type        varchar NOT NULL
);

ALTER TABLE eledger_config.el_transaction_type_gl_account
    ADD CONSTRAINT pk_transaction_type_gl_account
        PRIMARY KEY (id);

ALTER TABLE eledger_config.el_transaction_type_gl_account
    ADD CONSTRAINT fk_transaction_type_gl_account_trans_type
        FOREIGN KEY (transaction_type_id)
            REFERENCES eledger_config.el_transaction_type (id);

ALTER TABLE eledger_config.el_transaction_type_gl_account
    ADD CONSTRAINT fk_transaction_type_gl_account_gl_account
        FOREIGN KEY (gl_account_id)
            REFERENCES eledger_config.el_gl_account (id);


CREATE INDEX IF NOT EXISTS fki_transaction_type_gl_account_trans_type
    ON eledger_config.el_transaction_type_gl_account (transaction_type_id);

CREATE SEQUENCE eledger_config.el_transaction_type_gl_account_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_transaction_type_gl_account.id;

-----------------------------------------------------------------------------------------------------------------------------