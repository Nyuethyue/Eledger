-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE eledger_config.el_accounting_action
(
    id   int     NOT NULL,
    name varchar NOT NULL
);

ALTER TABLE eledger_config.el_accounting_action
    ADD CONSTRAINT pk_accounting_action
        PRIMARY KEY (id);

ALTER TABLE eledger_config.el_accounting_action
    ADD CONSTRAINT un_accounting_action_name UNIQUE (name);

CREATE SEQUENCE eledger_config.el_accounting_action_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_accounting_action.id;

-----------------------------------------------------------------------------------------------------------------------------  

CREATE TABLE eledger_config.el_accounting_action_type
(
    id                   bigint  NOT NULL,
    accounting_action_id int     NOT NULL,
    name                 varchar NOT NULL
);

ALTER TABLE eledger_config.el_accounting_action_type
    ADD CONSTRAINT pk_accounting_action_type
        PRIMARY KEY (id);

ALTER TABLE eledger_config.el_accounting_action_type
    ADD CONSTRAINT fk_accounting_action_type_accounting_action
        FOREIGN KEY (accounting_action_id)
            REFERENCES eledger_config.el_accounting_action (id);

CREATE SEQUENCE eledger_config.el_accounting_action_type_id_seq
    INCREMENT BY 1
    MINVALUE 1
    START 1
    CACHE 1
    NO CYCLE
    OWNED BY eledger_config.el_accounting_action_type.id;

-----------------------------------------------------------------------------------------------------------------------------  