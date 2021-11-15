------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.payment_mode
(
    id   bigint  NOT NULL,
    code char(32) NOT NULL
);

ALTER TABLE ref.payment_mode
    ADD CONSTRAINT pk_payment_mode
        PRIMARY KEY (id);

CREATE SEQUENCE ref.payment_mode_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.payment_mode.id;
------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.payment_mode_description
(
    id              bigint  NOT NULL,
    language_code   varchar NOT NULL,
    value           varchar NOT NULL,
    payment_mode_id bigint  NOT NULL
);

ALTER TABLE ref.payment_mode_description
    ADD CONSTRAINT pk_payment_mode_description
        PRIMARY KEY (id);

ALTER TABLE ref.payment_mode_description
    ADD CONSTRAINT un_payment_mode_description_payment_mode_id_lng_code
        UNIQUE (payment_mode_id, language_code);

ALTER TABLE ref.payment_mode_description
    ADD CONSTRAINT fk_payment_mode_description_payment_mode
        FOREIGN KEY (payment_mode_id)
            REFERENCES ref.payment_mode (id);

CREATE INDEX IF NOT EXISTS fki_payment_mode_description_payment_mode
    ON ref.payment_mode_description (payment_mode_id);

CREATE SEQUENCE ref.payment_mode_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.payment_mode_description.id;

