-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_interest_calculation
(
    id                        bigint  NOT NULL,
    transaction_type_id       bigint  NOT NULL,
    accounting_action_type_id bigint  NOT NULL,
    start_date_condition      varchar NOT NULL,
    days_count_condition      varchar NULL,
    percent_condition         varchar NOT NULL,
    max_amount_condition      varchar NULL,
    in_amount_condition       varchar NOT NULL,
    case_condition            varchar NOT NULL,
    start_date_function       varchar NULL,
    days_count_function       varchar NULL,
    percent_function          varchar NULL,
    max_amount_function       varchar NULL,
    in_amount_function        varchar NULL,
    case_function             varchar NULL,
    description               varchar NOT NULL,
    start_of_validity         date    NOT NULL,
    end_of_validity           date    NULL
);

ALTER TABLE eledger_config.el_interest_calculation
    ADD CONSTRAINT pk_interest_calculation PRIMARY KEY (id);

ALTER TABLE ONLY eledger_config.el_interest_calculation
    ADD CONSTRAINT fk_interest_calculation_acc_action_type
        FOREIGN KEY (accounting_action_type_id)
            REFERENCES eledger_config.el_accounting_action_type (id);

CREATE SEQUENCE eledger_config.el_interest_calculation_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_interest_calculation.id;


-----------------------------------------------------------------------------------------------------------------------------