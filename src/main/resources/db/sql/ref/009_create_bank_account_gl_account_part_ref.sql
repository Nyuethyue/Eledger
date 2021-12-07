----------------------------------------------------------------------------
CREATE TABLE ref.bank_account_gl_account_part
(
    id   bigint  NOT NULL,
    code varchar NOT NULL
);

ALTER TABLE ref.bank_account_gl_account_part
    ADD CONSTRAINT pk_bank_account_gl_account_part PRIMARY KEY (id);

CREATE SEQUENCE ref.bank_account_gl_account_part_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.bank_account_gl_account_part.id;