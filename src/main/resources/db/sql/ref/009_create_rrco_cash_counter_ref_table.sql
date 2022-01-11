------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.rrco_cash_counter
(
    id                bigint  NOT NULL,
    code              varchar NOT NULL,
    start_of_validity date    NOT NULL,
    end_of_validity   date    NULL
);

ALTER TABLE ref.rrco_cash_counter
    ADD CONSTRAINT pk_rrco_cash_counter
        PRIMARY KEY (id);

CREATE INDEX IF NOT EXISTS idx_rrco_cash_counter
    ON ref.rrco_cash_counter (code);

CREATE SEQUENCE ref.rrco_cash_counter_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.rrco_cash_counter.id;
------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.rrco_cash_counter_description
(
    id                      bigint  NOT NULL,
    language_code           varchar NOT NULL,
    value                   varchar NOT NULL,
    rrco_cash_counter_id   bigint  NOT NULL
);

ALTER TABLE ref.rrco_cash_counter_description
    ADD CONSTRAINT pk_rrco_cash_counter_description
        PRIMARY KEY (id);

ALTER TABLE ref.rrco_cash_counter_description
    ADD CONSTRAINT un_ref_rrco_cash_description_ref_rrco_cash_id_lng_code
        UNIQUE (rrco_cash_counter_id, language_code);

CREATE SEQUENCE ref.rrco_cash_counter_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.rrco_cash_counter_description.id;



