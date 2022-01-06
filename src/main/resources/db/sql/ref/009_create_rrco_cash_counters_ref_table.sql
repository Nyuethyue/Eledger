------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.rrco_cash_counters
(
    id                bigint  NOT NULL,
    code              varchar NOT NULL,
    start_of_validity date    NOT NULL,
    end_of_validity   date    NULL
);

ALTER TABLE ref.rrco_cash_counters
    ADD CONSTRAINT pk_rrco_cash_counters
        PRIMARY KEY (id);

CREATE INDEX IF NOT EXISTS idx_rrco_cash_counters
    ON ref.rrco_cash_counters (code);

CREATE SEQUENCE ref.rrco_cash_counters_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.rrco_cash_counters.id;
------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.rrco_cash_counters_description
(
    id                      bigint  NOT NULL,
    language_code           varchar NOT NULL,
    value                   varchar NOT NULL,
    rrco_cash_counters_id   bigint  NOT NULL
);

ALTER TABLE ref.rrco_cash_counters_description
    ADD CONSTRAINT pk_rrco_cash_counters_description
        PRIMARY KEY (id);

ALTER TABLE ref.rrco_cash_counters_description
    ADD CONSTRAINT un_ref_rrco_cash_description_ref_rrco_cash_id_lng_code
        UNIQUE (rrco_cash_counters_id, language_code);

CREATE SEQUENCE ref.rrco_cash_counters_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.rrco_cash_counters_description.id;



