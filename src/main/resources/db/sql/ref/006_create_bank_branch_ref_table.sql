--------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.bank_branch
(
    id               bigint  NOT NULL,
    code             varchar NOT NULL,
    bfsc_code        varchar NOT NULL,
    address          varchar NOT NULL,
    bank_id           bigint NOT NULL
);

ALTER TABLE ref.bank_branch
    ADD CONSTRAINT pk_bank_branch
        PRIMARY KEY (id);

ALTER TABLE ref.bank_branch
    ADD CONSTRAINT fk_bank_branch_description_bank
        FOREIGN KEY (bank_id)
            REFERENCES ref.bank(id);

CREATE SEQUENCE ref.bank_branch_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.bank_branch.id;
---------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.bank_branch_description
(
    id               bigint  NOT NULL,
    language_code    varchar NOT NULL,
    value            varchar NOT NULL,
    bank_branch_id   bigint  NOT NULL
);

ALTER TABLE ref.bank_branch_description
    ADD CONSTRAINT pk_ref_bank_branch_description
        PRIMARY KEY (id);

ALTER TABLE ref.bank_branch_description
    ADD CONSTRAINT un_ref_bank_branch_description_ref_bank_branch_id_lng_code
        UNIQUE (bank_branch_id, language_code);

ALTER TABLE ref.bank_branch_description
    ADD CONSTRAINT fk_bank_branch_description_bank
        FOREIGN KEY (bank_branch_id)
            REFERENCES ref.bank_branch (id);

CREATE INDEX IF NOT EXISTS fki_bank_branch_description_bank_branch
    ON ref.bank_branch_description(bank_branch_id);

CREATE SEQUENCE ref.bank_branch_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.bank_branch_description.id;

