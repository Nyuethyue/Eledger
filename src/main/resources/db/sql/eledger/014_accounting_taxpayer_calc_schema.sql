-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger.el_accounting
(
    id                        bigint         NOT NULL,
    parent_id                 bigint         NOT NULL,
    transaction_id            bigint         NOT NULL,
    gl_account_id             bigint         NULL,
    transfer_type             varchar        NOT NULL,
    account_type              varchar        NOT NULL,
    amount                    numeric(20, 2) NOT NULL,
    accounting_action_type_id int            NOT NULL,
    transaction_date          date           NOT NULL
);


ALTER TABLE eledger.el_accounting
    ADD CONSTRAINT pk_accounting
        PRIMARY KEY (id);

ALTER TABLE eledger.el_accounting
    ADD CONSTRAINT fk_accounting_transaction
        FOREIGN KEY (transaction_id)
            REFERENCES eledger.el_transaction (id);

CREATE INDEX IF NOT EXISTS fki_accounting_transaction
    ON eledger.el_accounting (transaction_id);

CREATE SEQUENCE eledger.el_accounting_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger.el_accounting.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger.el_taxpayer_calc
(
    id                 bigint    NOT NULL,
    tpn                varchar   NOT NULL,
    creation_date_time timestamp NOT NULL,
    calculated_date    date      NULL
);

ALTER TABLE eledger.el_taxpayer_calc
    ADD CONSTRAINT pk_taxpayer_calc
        PRIMARY KEY (id);

ALTER TABLE eledger.el_taxpayer_calc
    ADD CONSTRAINT un_taxpayer_calc_tpn UNIQUE (tpn);

-----------------------------------------------------------------------------------------------------------------------------