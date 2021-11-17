-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_gl_account_part_type
(
    id                          integer   NOT NULL,
    level                       integer   NULL,
    creation_date_time          timestamp NOT NULL,
    last_modification_date_time timestamp NOT NULL
);

ALTER TABLE eledger_config.el_gl_account_part_type
    ADD CONSTRAINT pk_gl_account_part_type PRIMARY KEY (id);

ALTER TABLE eledger_config.el_gl_account_part_type
    ADD CONSTRAINT un_gl_account_part_type_level UNIQUE (level);

CREATE SEQUENCE eledger_config.el_gl_account_part_type_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_gl_account_part_type.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_gl_account_part_type_description
(
    id                      bigint  NOT NULL,
    language_code           varchar NOT NULL,
    value                   varchar NULL,
    gl_account_part_type_id integer NOT NULL
);

ALTER TABLE eledger_config.el_gl_account_part_type_description
    ADD CONSTRAINT pk_gl_account_part_type_description PRIMARY KEY (id);

ALTER TABLE eledger_config.el_gl_account_part_type_description
    ADD CONSTRAINT un_bal_acc_part_type_dsc_bal_acc_part_type_id_lng_code
        UNIQUE (gl_account_part_type_id, language_code);

ALTER TABLE ONLY eledger_config.el_gl_account_part_type_description
    ADD CONSTRAINT fk_bal_acc_part_type_dsc_bal_acc_part_type
        FOREIGN KEY (gl_account_part_type_id)
            REFERENCES eledger_config.el_gl_account_part_type (id);

CREATE INDEX IF NOT EXISTS fki_bal_acc_part_type_dsc_bal_acc_part_type
    ON eledger_config.el_gl_account_part_type_description (gl_account_part_type_id);

CREATE SEQUENCE eledger_config.el_gl_account_part_type_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_gl_account_part_type_description.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_gl_account_part
(
    id                          bigint    NOT NULL,
    parent_id                   bigint    NULL,
    code                        varchar   NOT NULL,
    status                      varchar   NOT NULL,
    creation_date_time          timestamp NOT NULL,
    last_modification_date_time timestamp NOT NULL,
    start_of_validity           timestamp NULL,
    end_of_validity             timestamp NULL,
    gl_account_part_type_id     integer   NULL
);

ALTER TABLE eledger_config.el_gl_account_part
    ADD CONSTRAINT pk_gl_account_part PRIMARY KEY (id);

ALTER TABLE eledger_config.el_gl_account_part
    ADD CONSTRAINT fk_gl_account_part_gl_account_part_type
        FOREIGN KEY (gl_account_part_type_id)
            REFERENCES eledger_config.el_gl_account_part_type (id);

CREATE UNIQUE INDEX ux_parent_id_code ON eledger_config.el_gl_account_part USING btree (parent_id, code);

CREATE INDEX IF NOT EXISTS fki_gl_account_part_gl_account_part_type
    ON eledger_config.el_gl_account_part (gl_account_part_type_id);

CREATE INDEX IF NOT EXISTS idx_gl_account_part_code
    ON eledger_config.el_gl_account_part (code);

CREATE SEQUENCE eledger_config.el_gl_account_part_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_gl_account_part.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_gl_account_part_description
(
    id                 bigint  NOT NULL,
    language_code      varchar NOT NULL,
    value              varchar NULL,
    gl_account_part_id integer NOT NULL
);

ALTER TABLE eledger_config.el_gl_account_part_description
    ADD CONSTRAINT pk_gl_account_part_description PRIMARY KEY (id);

ALTER TABLE eledger_config.el_gl_account_part_description
    ADD CONSTRAINT un_bal_acc_part_dsc_bal_acc_part_id_lng_code
        UNIQUE (gl_account_part_id, language_code);

ALTER TABLE ONLY eledger_config.el_gl_account_part_description
    ADD CONSTRAINT fk_bal_acc_part_dsc_bal_acc_part FOREIGN KEY (gl_account_part_id)
        REFERENCES eledger_config.el_gl_account_part (id);

CREATE INDEX IF NOT EXISTS fki_bal_acc_part_dsc_bal_acc_part
    ON eledger_config.el_gl_account_part_description (gl_account_part_id);

CREATE SEQUENCE eledger_config.el_gl_account_part_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_gl_account_part_description.id;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_gl_account
(
    id                          bigint    NOT NULL,
    code                        varchar   NOT NULL,
    status                      varchar   NOT NULL,
    creation_date_time          timestamp NOT NULL,
    last_modification_date_time timestamp NOT NULL,
    start_of_validity           timestamp NULL,
    end_of_validity             timestamp NULL,
    gl_account_last_part_id     bigint    NOT NULL
);

ALTER TABLE eledger_config.el_gl_account
    ADD CONSTRAINT pk_gl_account PRIMARY KEY (id);

ALTER TABLE eledger_config.el_gl_account
    ADD CONSTRAINT fk_gl_account_gl_account_part
        FOREIGN KEY (gl_account_last_part_id)
            REFERENCES eledger_config.el_gl_account_part (id);

CREATE UNIQUE INDEX IF NOT EXISTS idx_gl_account_code
    ON eledger_config.el_gl_account (code);

CREATE SEQUENCE eledger_config.el_gl_account_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_gl_account.id;

-----------------------------------------------------------------------------------------------------------------------------


CREATE TABLE eledger_config.el_gl_account_description
(
    id            bigint  NOT NULL,
    language_code varchar NOT NULL,
    value         varchar NULL,
    gl_account_id integer NOT NULL
);

ALTER TABLE eledger_config.el_gl_account_description
    ADD CONSTRAINT pk_gl_account_description PRIMARY KEY (id);

ALTER TABLE eledger_config.el_gl_account_description
    ADD CONSTRAINT un_bal_acc_dsc_bal_acc_id_lng_code
        UNIQUE (gl_account_id, language_code);

ALTER TABLE ONLY eledger_config.el_gl_account_description
    ADD CONSTRAINT fk_bal_acc_dsc_bal_acc
        FOREIGN KEY (gl_account_id)
            REFERENCES eledger_config.el_gl_account (id);

CREATE INDEX IF NOT EXISTS fki_bal_acc_dsc_bal_acc
    ON eledger_config.el_gl_account_description (gl_account_id);

CREATE SEQUENCE eledger_config.el_gl_account_description_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_gl_account_description.id;

-----------------------------------------------------------------------------------------------------------------------------

