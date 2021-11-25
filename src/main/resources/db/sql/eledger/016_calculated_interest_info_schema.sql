-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger.el_calculated_interest_info
(
    id                        bigint         NOT NULL,
    transaction_id            bigint         NOT NULL,
    gl_account_id             bigint         NOT NULL,
    interest_calculation_id   bigint         NOT NULL,
    accounting_action_type_id bigint         NOT NULL,
    current_accounting_id     bigint         NOT NULL,
    amount                    numeric(20, 2) NOT NULL,
    calculated_days           bigint         NOT NULL,
    calculation_date          date           NOT NULL,
    orig_calculation_date     date           NULL
);


ALTER TABLE eledger.el_calculated_interest_info
    ADD CONSTRAINT pk_calculated_interest_info
        PRIMARY KEY (id);

ALTER TABLE eledger.el_calculated_interest_info
    ADD CONSTRAINT fk_calculated_interest_info_transaction
        FOREIGN KEY (transaction_id)
            REFERENCES eledger.el_transaction (id);

CREATE INDEX IF NOT EXISTS fki_calculated_interest_info_transaction
    ON eledger.el_calculated_interest_info (transaction_id);

CREATE SEQUENCE eledger.el_calculated_interest_info_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger.el_calculated_interest_info.id;

-----------------------------------------------------------------------------------------------------------------------------
