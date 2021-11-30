------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.currency
(
    id                bigint  NOT NULL,
    code              char(3) NOT NULL,
    symbol            varchar NOT NULL
);

ALTER TABLE ref.currency
    ADD CONSTRAINT pk_currency
        PRIMARY KEY (id);

CREATE SEQUENCE ref.currency_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.currency.id;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.currency_description
(
    id            bigint  NOT NULL,
    language_code varchar NOT NULL,
    value         varchar NOT NULL,
    currency_id   bigint  NOT NULL
);

ALTER TABLE ref.currency_description
    ADD CONSTRAINT pk_ref_currency_description
        PRIMARY KEY (id);

ALTER TABLE ref.currency_description
    ADD CONSTRAINT un_ref_currency_description_ref_currency_id_lng_code
        UNIQUE (currency_id, language_code);

ALTER TABLE ref.currency_description
    ADD CONSTRAINT fk_currency_description_currency
        FOREIGN KEY (currency_id)
            REFERENCES ref.currency (id);

CREATE INDEX IF NOT EXISTS fki_currency_description_currency
    ON ref.currency_description (currency_id);

CREATE SEQUENCE ref.currency_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.currency_description.id;

