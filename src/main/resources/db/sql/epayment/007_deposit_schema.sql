-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS epayment.deposit
(
    id                     bigint    NOT NULL,
    receipt_id             bigint    NOT NULL
    payment_mode           varchar   NOT NULL,
    bank_deposit_date      date NOT NULL,
    last_printed_date      timestamp NOT NULL,
    amount                 numeric(20, 2) NOT NULL,
    status                 varchar   NOT NULL,
    creation_date_time     timestamp NOT NULL,
);

ALTER TABLE epayment.deposit
    ADD CONSTRAINT pk_deposit PRIMARY KEY (id);

ALTER TABLE ONLY epayment.deposit
    ADD CONSTRAINT fk_deposit_payment_mode
        FOREIGN KEY (payment_mode) REFERENCES ref.payment_mode (id);

CREATE SEQUENCE epayment.deposit_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.deposit.id;
-----------------------------------------------------------------------------------------------------------------------------
