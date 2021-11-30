------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.denomination
(
    id     bigint  NOT NULL,
    dnm   decimal NOT NULL,

);

ALTER TABLE ref.denomination
    ADD CONSTRAINT pk_denomination
        PRIMARY KEY (id);

CREATE SEQUENCE ref.denomination_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.denomination.id;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.denomination_description
(
    id            bigint  NOT NULL,
    language_code varchar NOT NULL,
    value         varchar NOT NULL,
    denomination_id   bigint  NOT NULL
);

ALTER TABLE ref.denomination_description
    ADD CONSTRAINT pk_ref_denomination_description
        PRIMARY KEY (id);

ALTER TABLE ref.denomination_description
    ADD CONSTRAINT un_ref_denomination_description_ref_denomination_id_lng_code
        UNIQUE (denomination_id, language_code);

ALTER TABLE ref.denomination_description
    ADD CONSTRAINT fk_denomination_description_denomination
        FOREIGN KEY (denomination_id)
            REFERENCES ref.denomination (id);

CREATE INDEX IF NOT EXISTS fki_denomination_description_denomination
    ON ref.denomination_description (denomination_id);

CREATE SEQUENCE ref.denomination_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.denomination_description.id;

