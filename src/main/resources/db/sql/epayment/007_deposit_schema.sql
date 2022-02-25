-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS epayment.ep_deposit
(
    id                     bigint   NOT NULL,
    deposit_number         VARCHAR NOT NULL,
    payment_mode_id        bigint   NOT NULL,
    bank_deposit_date      date     NOT NULL,
    last_printed_date      timestamp,
    amount                 numeric(20, 2) NOT NULL,
    status                 varchar   NOT NULL,
    creation_date_time     timestamp NOT NULL
);

ALTER TABLE epayment.ep_deposit
    ADD CONSTRAINT pk_ep_deposit PRIMARY KEY (id);

CREATE SEQUENCE epayment.ep_deposit_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_deposit.id;
-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS epayment.ep_deposit_receipt
(
    id                     bigint    NOT NULL,
    deposit_id             bigint    NOT NULL,
    receipt_id             bigint    NOT NULL,
    receipt_number         varchar
);

ALTER TABLE epayment.ep_deposit_receipt
    ADD CONSTRAINT pk_ep_deposit_receipt PRIMARY KEY (id);

ALTER TABLE ONLY epayment.ep_deposit_receipt
    ADD CONSTRAINT fk_ep_deposit_deposit_id
        FOREIGN KEY (deposit_id) REFERENCES epayment.ep_deposit (id);

ALTER TABLE ONLY epayment.ep_deposit_receipt
    ADD CONSTRAINT fk_ep_deposit_receipt_id
        FOREIGN KEY (receipt_id) REFERENCES epayment.ep_receipt (id);


CREATE SEQUENCE epayment.ep_deposit_receipt_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_deposit_receipt.id;
-----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS epayment.ep_deposit_denomination_counts
(
    id                     bigint    NOT NULL,
    deposit_id             bigint    NOT NULL,
    denomination_id        bigint    NOT NULL,
    "count"                bigint    NOT NULL
);

ALTER TABLE epayment.ep_deposit_denomination_counts
    ADD CONSTRAINT pk_ep_deposit_denomination_counts PRIMARY KEY (id);


CREATE SEQUENCE epayment.ep_deposit_denomination_counts_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_deposit_denomination_counts.id;
-----------------------------------------------------------------------------------------------------------------------------