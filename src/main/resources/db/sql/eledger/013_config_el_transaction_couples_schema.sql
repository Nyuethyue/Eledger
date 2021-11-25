-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_transaction_couples
(
    id                               bigint NOT NULL,
    accounting_action_type_id        int    NOT NULL,
    from_transaction_type_account_id bigint NOT NULL,
    to_transaction_type_account_id   bigint NOT NULL
);

ALTER TABLE eledger_config.el_transaction_couples
    ADD CONSTRAINT pk_transaction_couples
        PRIMARY KEY (id);

ALTER TABLE eledger_config.el_transaction_couples
    ADD CONSTRAINT fk_transaction_couples_accounting_action_type
        FOREIGN KEY (accounting_action_type_id)
            REFERENCES eledger_config.el_accounting_action_type (id);

CREATE INDEX IF NOT EXISTS fki_transaction_couples_accounting_action_type
    ON eledger_config.el_transaction_couples (accounting_action_type_id);

CREATE SEQUENCE eledger_config.el_transaction_couples_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_transaction_couples.id;

-----------------------------------------------------------------------------------------------------------------------------