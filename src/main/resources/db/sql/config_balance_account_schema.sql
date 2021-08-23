-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account_part_type
(
    id    integer NOT NULL,
    level integer NULL
);

ALTER TABLE config.balance_account_part_type
    ADD CONSTRAINT pk_balance_account_part_type PRIMARY KEY (id);

ALTER TABLE config.balance_account_part_type
    ADD CONSTRAINT un_balance_account_part_type_level UNIQUE (level);

CREATE SEQUENCE config.balance_account_part_type_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY config.balance_account_part_type.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account_part_type_description
(
    id                           bigint  NOT NULL,
    language_code                varchar NOT NULL,
    value                        varchar NULL,
    balance_account_part_type_id integer NOT NULL
);

ALTER TABLE config.balance_account_part_type_description
    ADD CONSTRAINT pk_balance_account_part_type_description PRIMARY KEY (id);

ALTER TABLE config.balance_account_part_type_description
    ADD CONSTRAINT un_bal_acc_part_type_dsc_bal_acc_part_type_id_lng_code
        UNIQUE (balance_account_part_type_id, language_code);

ALTER TABLE ONLY config.balance_account_part_type_description
    ADD CONSTRAINT fk_bal_acc_part_type_dsc_bal_acc_part_type
        FOREIGN KEY (balance_account_part_type_id)
            REFERENCES config.balance_account_part_type (id);

CREATE INDEX IF NOT EXISTS fki_bal_acc_part_type_dsc_bal_acc_part_type
    ON config.balance_account_part_type_description (balance_account_part_type_id);

CREATE SEQUENCE config.balance_account_part_type_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY config.balance_account_part_type_description.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account_part
(
    id                           bigint    NOT NULL,
    parent_id                    bigint    NULL,
    code                         varchar   NOT NULL,
    status                       varchar   NOT NULL,
    start_date                   timestamp NULL,
    end_date                     timestamp NULL,
    balance_account_part_type_id integer   NULL
);

ALTER TABLE config.balance_account_part
    ADD CONSTRAINT pk_balance_account_part PRIMARY KEY (id);

ALTER TABLE config.balance_account_part
    ADD CONSTRAINT fk_balance_account_part_balance_account_part_type
        FOREIGN KEY (balance_account_part_type_id)
            REFERENCES config.balance_account_part_type (id);

CREATE UNIQUE INDEX ux_parent_id_code ON config.balance_account_part USING btree (parent_id, code);

CREATE SEQUENCE config.balance_account_part_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY config.balance_account_part.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account_part_description
(
    id                      bigint  NOT NULL,
    language_code           varchar NOT NULL,
    value                   varchar NULL,
    balance_account_part_id integer NOT NULL
);

ALTER TABLE config.balance_account_part_description
    ADD CONSTRAINT pk_balance_account_part_description PRIMARY KEY (id);

ALTER TABLE config.balance_account_part_description
    ADD CONSTRAINT un_bal_acc_part_dsc_bal_acc_part_id_lng_code
        UNIQUE (balance_account_part_id, language_code);

ALTER TABLE ONLY config.balance_account_part_description
    ADD CONSTRAINT fk_bal_acc_part_dsc_bal_acc_part FOREIGN KEY (balance_account_part_id)
        REFERENCES config.balance_account_part (id);

CREATE INDEX IF NOT EXISTS fki_bal_acc_part_dsc_bal_acc_part
    ON config.balance_account_part_description (balance_account_part_id);

CREATE SEQUENCE config.balance_account_part_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY config.balance_account_part_description.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account
(
    id                           bigint    NOT NULL,
    code                         varchar   NOT NULL,
    status                       varchar   NOT NULL,
    start_date                   timestamp NULL,
    end_date                     timestamp NULL,
    balance_account_last_part_id bigint    NOT NULL
);

ALTER TABLE config.balance_account
    ADD CONSTRAINT pk_balance_account PRIMARY KEY (id);

ALTER TABLE config.balance_account
    ADD CONSTRAINT fk_balance_account_balance_account_part
        FOREIGN KEY (balance_account_last_part_id)
            REFERENCES config.balance_account_part (id);

CREATE SEQUENCE config.balance_account_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY config.balance_account.id;

-----------------------------------------------------------------------------------------------------------------------------


CREATE TABLE config.balance_account_description
(
    id                 bigint  NOT NULL,
    language_code      varchar NOT NULL,
    value              varchar NULL,
    balance_account_id integer NOT NULL
);

ALTER TABLE config.balance_account_description
    ADD CONSTRAINT pk_balance_account_description PRIMARY KEY (id);

ALTER TABLE config.balance_account_description
    ADD CONSTRAINT un_bal_acc_dsc_bal_acc_id_lng_code
        UNIQUE (balance_account_id, language_code);

ALTER TABLE ONLY config.balance_account_description
    ADD CONSTRAINT fk_bal_acc_dsc_bal_acc
        FOREIGN KEY (balance_account_id)
            REFERENCES config.balance_account (id);

CREATE INDEX IF NOT EXISTS fki_bal_acc_dsc_bal_acc
    ON config.balance_account_description (balance_account_id);

CREATE SEQUENCE config.balance_account_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY config.balance_account_description.id;

-----------------------------------------------------------------------------------------------------------------------------

