--------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.open_close_tax_period_config
(
    id                                  bigint  NOT NULL,
    gl_account_part_full_code           varchar NOT NULL,
    calendar_year                       integer NOT NULL,
    tax_period_type_id                  bigint  NOT NULL,
    transaction_type_id                 bigint  NOT NULL,
    years                               integer NULL,
    month                               integer NULL
);

ALTER TABLE ref.open_close_tax_period_config
    ADD CONSTRAINT pk_open_close_tax_period_config
        PRIMARY KEY (id);

ALTER TABLE ref.open_close_tax_period_config
    ADD CONSTRAINT un_tax_type_code_calen_year_tax_period_type_id_trans_type_id
        UNIQUE (gl_account_part_full_code, calendar_year, tax_period_type_id, transaction_type_id);

ALTER TABLE ONLY ref.open_close_tax_period_config
    ADD CONSTRAINT fk_open_close_tax_period_config_transaction_type_id
    FOREIGN KEY (transaction_type_id)
    REFERENCES eledger_config.el_transaction_type (id);

CREATE SEQUENCE ref.open_close_tax_period_config_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.open_close_tax_period_config.id;
--------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.open_close_tax_period_config_record
(
    id                              bigint  NOT NULL,
    period_id                       bigint  NOT NULL,
    period                          varchar  NOT NULL,
    open_close_tax_period_config_id bigint  NOT NULL,
    period_open_date                date    NOT NULL,
    period_close_date               date    NOT NULL
);
ALTER TABLE ref.open_close_tax_period_config_record
    ADD CONSTRAINT pk_open_close_tax_period_config_record
        PRIMARY KEY (id);

CREATE SEQUENCE ref.open_close_tax_period_config_record_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.open_close_tax_period_config_record.id;