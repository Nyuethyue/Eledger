-----------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.agency_gl_account
(
    id                              bigint  NOT NULL,
    code                            varchar NOT NULL,
    agency_code                     varchar NOT NULL
);

ALTER TABLE ref.agency_gl_account
    ADD CONSTRAINT pk_agency_gl_account
        PRIMARY KEY (id);

CREATE INDEX IF NOT EXISTS idx_agency_gl_account_code
    ON ref.agency_gl_account (code);

CREATE SEQUENCE ref.agency_gl_account_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.agency_gl_account.id;
