--------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.holiday_date
(
    id                    bigint  NOT NULL,
    year                  varchar NOT NULL,
    holiday_start_date    date    NOT NULL,
    holiday_end_date      date    NOT NULL,
    is_valid_for_one_year boolean NOT NULL
);

ALTER TABLE ref.holiday_date
    ADD CONSTRAINT pk_holiday_date
        PRIMARY KEY (id);

CREATE SEQUENCE ref.holiday_date_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.holiday_date.id;
--------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.holiday_date_description
(
    id                      bigint  NOT NULL,
    language_code           varchar NOT NULL,
    value                   varchar NOT NULL,
    holiday_date_id   bigint  NOT NULL
);
ALTER TABLE ref.holiday_date_description
    ADD CONSTRAINT pk_holiday_date_description
        PRIMARY KEY (id);

ALTER TABLE ref.holiday_date_description
    ADD CONSTRAINT un_ref_holiday_date_description_ref_holiday_date_id_lng_code
        UNIQUE (holiday_date_id, language_code);

CREATE SEQUENCE ref.holiday_date_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.holiday_date_description.id;






