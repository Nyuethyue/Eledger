-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS epayment.ep_gl_account
(
    id                 bigint    NOT NULL,
    code               varchar   NOT NULL,
    creation_date_time timestamp NOT NULL
);

ALTER TABLE epayment.ep_gl_account
    ADD CONSTRAINT pk_pa_gl_account
        PRIMARY KEY (id);

CREATE SEQUENCE epayment.ep_gl_account_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_gl_account.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS epayment.ep_gl_account_description
(
    id            bigint  NOT NULL,
    language_code varchar NOT NULL,
    value         varchar NOT NULL,
    gl_account_id bigint  NOT NULL
);

ALTER TABLE epayment.ep_gl_account_description
    ADD CONSTRAINT pk_pa_gl_account_description
        PRIMARY KEY (id);

ALTER TABLE epayment.ep_gl_account_description
    ADD CONSTRAINT un_pa_gl_account_description_pa_gl_account_id_lng_code
        UNIQUE (gl_account_id, language_code);

ALTER TABLE epayment.ep_gl_account_description
    ADD CONSTRAINT fk_pa_gl_account_description_pa_gl_account
        FOREIGN KEY (gl_account_id)
            REFERENCES epayment.ep_gl_account (id);

CREATE INDEX IF NOT EXISTS fki_gl_account_description_gl_account
    ON epayment.ep_gl_account_description (gl_account_id);

CREATE SEQUENCE epayment.ep_gl_account_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY epayment.ep_gl_account_description.id;


-----------------------------------------------------------------------------------------------------------------------------
