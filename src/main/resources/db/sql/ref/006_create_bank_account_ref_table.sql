------------------------------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.bank_account
(
    id                bigint  NOT NULL,
    branch_id         bigint  NOT NULL,
    code        varchar NOT NULL,
    start_of_validity date    NOT NULL,
    end_of_validity   date    NULL
);

ALTER TABLE ref.bank_account
    ADD CONSTRAINT pk_bank_account
        PRIMARY KEY (id);

ALTER TABLE ref.bank_account
    ADD CONSTRAINT fk_bank_account
        FOREIGN KEY (branch_id)
            REFERENCES ref.bank_branch (id);

CREATE INDEX IF NOT EXISTS fki_bank_branch_account
    ON ref.bank_account (branch_id);

CREATE SEQUENCE ref.bank_account_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.bank_account.id;
---------------------------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.bank_account_description
(
    id              bigint  NOT NULL,
    language_code   varchar NOT NULL,
    value           varchar NOT NULL,
    bank_account_id bigint  NOT NULL
);

ALTER TABLE ref.bank_account_description
    ADD CONSTRAINT pk_ref_bank_account_description
        PRIMARY KEY (id);

ALTER TABLE ref.bank_account_description
    ADD CONSTRAINT un_ref_bank_account_description_ref_bank_account_id_lng_code
        UNIQUE (bank_account_id, language_code);

ALTER TABLE ref.bank_account_description
    ADD CONSTRAINT fk_bank_account_description_bank
        FOREIGN KEY (bank_account_id)
            REFERENCES ref.bank_account (id);

CREATE INDEX IF NOT EXISTS fki_bank_account_description_bank_account
    ON ref.bank_account_description (bank_account_id);

CREATE SEQUENCE ref.bank_account_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.bank_account_description.id;