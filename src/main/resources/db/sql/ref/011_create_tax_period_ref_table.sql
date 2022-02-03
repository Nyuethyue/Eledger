--------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.tax_period_config
(
    id                                  bigint  NOT NULL,
    gl_account_part_full_code           varchar NOT NULL,
    calendar_year                       integer NOT NULL,
    tax_period_type_id                  bigint  NOT NULL,
    transaction_type_id                 bigint  NOT NULL,
    due_date_count_for_return_filing    integer NOT NULL,
    due_date_count_for_payment          integer NOT NULL,
    valid_from                          date    NOT NULL,
    valid_to                            date,
    consider_non_working_days           boolean NOT NULL
);

ALTER TABLE ref.tax_period_config
    ADD CONSTRAINT pk_tax_period_config
        PRIMARY KEY (id);

ALTER TABLE ref.tax_period_config
    ADD CONSTRAINT un_tax_type_code_calendar_year_tax_period_type_id_transaction_type_id
        UNIQUE (gl_account_part_full_code, calendar_year, tax_period_type_id, transaction_type_id);

ALTER TABLE ONLY ref.tax_period_config
    ADD CONSTRAINT fk_tax_period_config_transaction_type_id
        FOREIGN KEY (transaction_type_id)
            REFERENCES eledger_config.el_transaction_type (id);

CREATE SEQUENCE ref.tax_period_config_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.non_working_days.id;
--------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS ref.ref_tax_period_record
(
    id                              bigint  NOT NULL,
    period_id                       bigint  NOT NULL,
    tax_period_config_id            bigint  NOT NULL,
    period_start_date               date    NOT NULL,
    period_end_date                 date    NOT NULL,
    filing_due_date                 date    NOT NULL,
    payment_due_date                date    NOT NULL,
    interest_calc_start_day         date    NOT NULL,
    fine_and_penalty_calc_start_day date    NOT NULL,
    valid_from                      date    NOT NULL,
    remark                          varchar NOT NULL
);
ALTER TABLE ref.ref_tax_period_record
    ADD CONSTRAINT pk_ref_tax_period_record
        PRIMARY KEY (id);

CREATE SEQUENCE ref.ref_tax_period_record_id_seq
    INCREMENT BY 1
    MINVALUE 1
    CACHE 1
    NO CYCLE
    OWNED BY ref.non_working_days_description.id;






