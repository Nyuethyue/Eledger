--------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.open_close_tax_period_config
(
    id                        bigint  NOT NULL,
    gl_account_part_full_code varchar NOT NULL,
    calendar_year             integer NOT NULL,
    tax_period_code           varchar NOT NULL,
    transaction_type_id       bigint  NOT NULL,
    no_of_years               integer NULL,
    no_of_month               integer NULL
);

ALTER TABLE ref.open_close_tax_period_config
    ADD CONSTRAINT pk_open_close_tax_period_config
        PRIMARY KEY (id);

ALTER TABLE ref.open_close_tax_period_config
    ADD CONSTRAINT un_tax_type_code_calen_year_tax_period_type_id_trans_type_id
        UNIQUE (gl_account_part_full_code, calendar_year, tax_period_code, transaction_type_id);

ALTER TABLE ONLY ref.open_close_tax_period_config
    ADD CONSTRAINT fk_open_close_tax_period_config_transaction_type_id
        FOREIGN KEY (transaction_type_id)
            REFERENCES eledger_config.el_transaction_type (id);

ALTER TABLE ONLY ref.open_close_tax_period_config
    ADD CONSTRAINT fk_open_close_tax_period_config_tax_period_code
        FOREIGN KEY (tax_period_code)
            REFERENCES ref.tax_period (code);

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
    id                              bigint NOT NULL,
    period_segment_id               bigint NOT NULL,
    open_close_tax_period_config_id bigint NOT NULL,
    period_open_date                date   NOT NULL,
    period_close_date               date   NOT NULL
);
ALTER TABLE ref.open_close_tax_period_config_record
    ADD CONSTRAINT pk_open_close_tax_period_config_record
        PRIMARY KEY (id);

ALTER TABLE ONLY ref.open_close_tax_period_config_record
    ADD CONSTRAINT fk_open_close_tax_period_config_record_tax_period_config_id
        FOREIGN KEY (open_close_tax_period_config_id)
            REFERENCES ref.open_close_tax_period_config (id);

ALTER TABLE ONLY ref.open_close_tax_period_config_record
    ADD CONSTRAINT fk_open_close_tax_period_config_record_period_segment_id
        FOREIGN KEY (period_segment_id)
            REFERENCES ref.tax_period_segment (id);

CREATE SEQUENCE ref.open_close_tax_period_config_record_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.open_close_tax_period_config_record.id;