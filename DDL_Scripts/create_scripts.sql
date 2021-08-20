-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account_part_type (
	id int4 NOT NULL,
	description jsonb NOT NULL,
	"level" int4 NULL,
	CONSTRAINT pk_balance_account_part_type PRIMARY KEY (id)
);

CREATE SEQUENCE config.seq_balance_account_part_type
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account_part (
	id int8 NOT NULL,
	parent_id int8 NULL,
	description jsonb NOT NULL,
	code varchar NOT NULL,
	balance_account_part_type_id int4 NULL,
	start_date timestamp NULL,
	end_date timestamp NULL,
	CONSTRAINT pk_balance_account_part PRIMARY KEY (id),
	CONSTRAINT fk_balance_account_part_type FOREIGN KEY (balance_account_part_type_id) REFERENCES config.balance_account_part_type(id)
);

CREATE UNIQUE INDEX idx_parent_id_code ON config.balance_account_part USING btree (parent_id, code);

CREATE SEQUENCE config.seq_balance_account_part_type
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account (
	id int8 NOT NULL,
	code varchar NOT NULL,
	description jsonb NOT NULL,
	balance_account_last_part_id int8 NOT NULL,
	is_debit bool NULL,
	CONSTRAINT pk_balance_account PRIMARY KEY (id),
	CONSTRAINT fk_balance_account_part FOREIGN KEY (balance_account_last_part_id) REFERENCES config.balance_account_part(id)
);

CREATE SEQUENCE config.seq_balance_account
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

-----------------------------------------------------------------------------------------------------------------------------

