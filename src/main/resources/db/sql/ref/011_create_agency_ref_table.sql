-------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.agency
(
    id                bigint  NOT NULL,
    code              varchar NOT NULL,
    start_of_validity date    NOT NULL,
    end_of_validity   date    NULL
);

ALTER TABLE ref.agency
    ADD CONSTRAINT pk_agency
        PRIMARY KEY (id);

CREATE INDEX IF NOT EXISTS idx_agency_code
    ON ref.agency (code);

CREATE SEQUENCE ref.agency_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.agency.id;
------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.agency_description
(
    id               bigint  NOT NULL,
    language_code    varchar NOT NULL,
    value            varchar NOT NULL,
    agency_id        bigint  NOT NULL
);

ALTER TABLE ref.agency_description
    ADD CONSTRAINT pk_ref_agency_description
        PRIMARY KEY (id);

ALTER TABLE ref.agency_description
    ADD CONSTRAINT un_ref_agency_description_ref_agency_id_lng_code
        UNIQUE (agency_id, language_code);

ALTER TABLE ref.agency_description
    ADD CONSTRAINT fk_agency_description_bank
        FOREIGN KEY (agency_id)
            REFERENCES ref.agency(id);

CREATE INDEX IF NOT EXISTS fki_agency_description_agency
    ON ref.agency_description (agency_id);

CREATE SEQUENCE ref.agency_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.agency_description.id;