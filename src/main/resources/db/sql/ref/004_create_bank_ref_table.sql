---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.bank
(
    id                bigint  NOT NULL,
    code              varchar NOT NULL,
    start_of_validity date    NOT NULL,
    end_of_validity   date    NULL
);

ALTER TABLE ref.bank
    ADD CONSTRAINT pk_bank
        PRIMARY KEY (id);

CREATE INDEX IF NOT EXISTS idx_bank_code
    ON ref.bank (code);

CREATE SEQUENCE ref.bank_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.bank.id;
------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.bank_description
(
    id            bigint  NOT NULL,
    language_code varchar NOT NULL,
    value         varchar NOT NULL,
    bank_id   bigint  NOT NULL
);

ALTER TABLE ref.bank_description
    ADD CONSTRAINT pk_ref_bank_description
        PRIMARY KEY (id);

ALTER TABLE ref.bank_description
    ADD CONSTRAINT un_ref_bank_description_ref_bank_id_lng_code
        UNIQUE (bank_id, language_code);

ALTER TABLE ref.bank_description
    ADD CONSTRAINT fk_bank_description_bank
        FOREIGN KEY (bank_id)
            REFERENCES ref.bank(id);

CREATE INDEX IF NOT EXISTS fki_bank_description_bank
    ON ref.bank_description (bank_id);

CREATE SEQUENCE ref.bank_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.bank_description.id;