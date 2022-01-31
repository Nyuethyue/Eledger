--------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.non_working_days
(
    id                     bigint  NOT NULL,
    year                   varchar NOT NULL,
    start_day              int     NOT NULL,
    end_day                int     NOT NULL,
    start_month            int     NOT NULL,
    end_month              int     NOT NULL,
    start_of_validity      date    NOT NULL,
    end_of_validity        date    NOT NULL
);

ALTER TABLE ref.non_working_days
    ADD CONSTRAINT pk_non_working_days
        PRIMARY KEY (id);

CREATE SEQUENCE ref.non_working_days_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.non_working_days.id;
--------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.non_working_days_description
(
    id                      bigint  NOT NULL,
    language_code           varchar NOT NULL,
    value                   varchar NOT NULL,
    non_working_days_id     bigint  NOT NULL
);
ALTER TABLE ref.non_working_days_description
    ADD CONSTRAINT pk_ref_non_working_days_description
        PRIMARY KEY (id);

ALTER TABLE ref.non_working_days_description
    ADD CONSTRAINT un_ref_non_working_days_des_ref_non_working_days_id_lng_code
        UNIQUE (non_working_days_id, language_code);

CREATE SEQUENCE ref.non_working_days_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.non_working_days_description.id;






