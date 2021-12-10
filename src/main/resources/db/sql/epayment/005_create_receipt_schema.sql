-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE epayment.ep_receipt
(
    id                     bigint         NOT NULL,
    drn                    varchar        NOT NULL,
    payment_mode           varchar        NOT NULL,
    status                 varchar        NOT NULL,
    ref_currency_id        bigint         NOT NULL,
    ref_bank_branch_id     bigint,
    receipt_number         varchar        NOT NULL,
    security_number        varchar,
    instrument_number      varchar,
    instrument_date        date,
    other_reference_number varchar,
    creation_date_time     timestamp      NOT NULL,
    taxpayer_id            bigint         NOT NULL,
    total_paid_amount      numeric(20, 2) NOT NULL,
    pan                    varchar        NOT NULL
);

ALTER TABLE epayment.ep_receipt
    ADD CONSTRAINT pk_receipt PRIMARY KEY (id);

CREATE UNIQUE INDEX IF NOT EXISTS idx_receipt_receipt_number
    ON epayment.ep_receipt (receipt_number);

-- ALTER TABLE ONLY epayment.ep_receipt
--     ADD CONSTRAINT fk_payment_advice_pa_bank_info
--         FOREIGN KEY (pa_bank_info_id) REFERENCES epayment.ep_pa_bank_info (id);


ALTER TABLE ONLY epayment.ep_receipt
    ADD CONSTRAINT fk_receipt_taxpayer
        FOREIGN KEY (taxpayer_id) REFERENCES epayment.ep_taxpayer (id);

CREATE INDEX IF NOT EXISTS fki_payment_advice_pa_bank_info
    ON epayment.ep_payment_advice (pa_bank_info_id);

CREATE INDEX IF NOT EXISTS fki_receipt_taxpayer
    ON epayment.ep_receipt (taxpayer_id);

CREATE SEQUENCE epayment.ep_receipt_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_receipt.id;


-----------------------------------------------------------------------------------------------------------------------------


CREATE TABLE IF NOT EXISTS epayment.ep_payment
(
    id                       bigint         NOT NULL,
    paid_amount              numeric(20, 2) NOT NULL DEFAULT 0,
    payable_line_id          bigint         NOT NULL,
    el_target_transaction_id bigint         NOT NULL,
    gl_account_id            bigint         NOT NULL,
    receipt_id               bigint         NOT NULL
);

ALTER TABLE epayment.ep_payment
    ADD CONSTRAINT pk_ep_payment PRIMARY KEY (id);

ALTER TABLE ONLY epayment.ep_payment
    ADD CONSTRAINT fk_payment_payable_line
        FOREIGN KEY (payable_line_id) REFERENCES epayment.ep_pa_payable_line (id);

ALTER TABLE ONLY epayment.ep_payment
    ADD CONSTRAINT fk_payment_gl_account
        FOREIGN KEY (gl_account_id) REFERENCES epayment.ep_gl_account (id);

CREATE SEQUENCE epayment.ep_payment_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_payment.id;
