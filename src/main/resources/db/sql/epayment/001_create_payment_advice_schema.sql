-------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS epayment.ep_pa_bank_info
(
    id                  bigint  NOT NULL,
    bank_account_number varchar NOT NULL
);

ALTER TABLE epayment.ep_pa_bank_info
    ADD CONSTRAINT pk_pa_bank_info
        PRIMARY KEY (id);

CREATE SEQUENCE epayment.ep_pa_bank_info_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_pa_bank_info.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS epayment.ep_pa_bank_info_description
(
    id              bigint  NOT NULL,
    language_code   varchar NOT NULL,
    value           varchar NOT NULL,
    pa_bank_info_id bigint  NOT NULL
);

ALTER TABLE epayment.ep_pa_bank_info_description
    ADD CONSTRAINT pk_pa_bank_info_description
        PRIMARY KEY (id);

ALTER TABLE epayment.ep_pa_bank_info_description
    ADD CONSTRAINT un_pa_bank_info_description_pa_bank_info_id_lng_code
        UNIQUE (pa_bank_info_id, language_code);

ALTER TABLE epayment.ep_pa_bank_info_description
    ADD CONSTRAINT fk_pa_bank_info_description_pa_bank_info
        FOREIGN KEY (pa_bank_info_id)
            REFERENCES epayment.ep_pa_bank_info (id);

CREATE INDEX IF NOT EXISTS fki_pa_bank_info_description_pa_bank_info
    ON epayment.ep_pa_bank_info_description (pa_bank_info_id);

CREATE SEQUENCE epayment.ep_pa_bank_info_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_pa_bank_info_description.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS epayment.ep_pa_gl_account
(
    id   bigint  NOT NULL,
    code varchar NOT NULL
);

ALTER TABLE epayment.ep_pa_gl_account
    ADD CONSTRAINT pk_pa_gl_account
        PRIMARY KEY (id);

CREATE SEQUENCE epayment.ep_pa_gl_account_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_pa_gl_account.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS epayment.ep_pa_gl_account_description
(
    id               bigint  NOT NULL,
    language_code    varchar NOT NULL,
    value            varchar NOT NULL,
    pa_gl_account_id bigint  NOT NULL
);

ALTER TABLE epayment.ep_pa_gl_account_description
    ADD CONSTRAINT pk_pa_gl_account_description
        PRIMARY KEY (id);

ALTER TABLE epayment.ep_pa_gl_account_description
    ADD CONSTRAINT un_pa_gl_account_description_pa_gl_account_id_lng_code
        UNIQUE (pa_gl_account_id, language_code);

ALTER TABLE epayment.ep_pa_gl_account_description
    ADD CONSTRAINT fk_pa_gl_account_description_pa_gl_account
        FOREIGN KEY (pa_gl_account_id)
            REFERENCES epayment.ep_pa_gl_account (id);

CREATE INDEX IF NOT EXISTS fki_pa_gl_account_description_pa_bank_info
    ON epayment.ep_pa_bank_info_description (pa_bank_info_id);

CREATE SEQUENCE epayment.ep_pa_gl_account_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_pa_gl_account_description.id;


-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE epayment.ep_payment_advice
(
    id                 bigint    NOT NULL,
    drn                varchar   NOT NULL,
    tpn                varchar   NOT NULL,
    status             varchar   NOT NULL,
    due_date           date      NOT NULL,
    period_start_date  date      NOT NULL,
    period_end_date    date      NOT NULL,
    creation_date_time timestamp NOT NULL,
    pan                varchar   NOT NULL,
    pa_bank_info_id    bigint    NOT NULL
);

ALTER TABLE epayment.ep_payment_advice
    ADD CONSTRAINT pk_payment_advice PRIMARY KEY (id);

CREATE UNIQUE INDEX IF NOT EXISTS idx_payment_advice_pan
    ON epayment.ep_payment_advice (pan);

ALTER TABLE ONLY epayment.ep_payment_advice
    ADD CONSTRAINT fk_payment_advice_pa_bank_info
        FOREIGN KEY (pa_bank_info_id) REFERENCES epayment.ep_pa_bank_info (id);

CREATE INDEX IF NOT EXISTS fki_payment_advice_pa_bank_info
    ON epayment.ep_payment_advice (pa_bank_info_id);

CREATE SEQUENCE epayment.ep_payment_advice_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_payment_advice.id;


-----------------------------------------------------------------------------------------------------------------------------


CREATE TABLE IF NOT EXISTS epayment.ep_pa_payment_line
(
    id                bigint         NOT NULL,
    amount            numeric(20, 2) NOT NULL,
    paid_amount       numeric(20, 2) NOT NULL DEFAULT 0,
    pa_gl_account_id  bigint         NOT NULL,
    payment_advice_id bigint         NOT NULL
);

ALTER TABLE epayment.ep_pa_payment_line
    ADD CONSTRAINT pk_pa_payment_line PRIMARY KEY (id);

ALTER TABLE ONLY epayment.ep_pa_payment_line
    ADD CONSTRAINT fk_pa_payment_line_payment_advice
        FOREIGN KEY (payment_advice_id) REFERENCES epayment.ep_payment_advice (id);

ALTER TABLE ONLY epayment.ep_pa_payment_line
    ADD CONSTRAINT fk_pa_payment_line_pa_gl_account
        FOREIGN KEY (pa_gl_account_id) REFERENCES epayment.ep_pa_gl_account (id);

CREATE SEQUENCE epayment.ep_pa_payment_line_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_pa_payment_line.id;
