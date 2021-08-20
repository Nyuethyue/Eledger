-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account_part_type (
	id int NOT NULL,
	description jsonb NOT NULL,
	"level" int NULL,
	CONSTRAINT pk_balance_account_part_type PRIMARY KEY (id)
);

CREATE SEQUENCE config.seq_balance_account_part_type
	INCREMENT BY 1
	MINVALUE 1
	START 1
	CACHE 1
	NO CYCLE;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account_part (
	id bigint NOT NULL,
	parent_id bigint NULL,
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
	START 1
	CACHE 1
	NO CYCLE;

-----------------------------------------------------------------------------------------------------------------------------

CREATE TABLE config.balance_account (
	id bigint NOT NULL,
	code varchar NOT NULL,
	description jsonb NOT NULL,
	balance_account_last_part_id bigint NOT NULL,
	is_debit bool NULL,
	CONSTRAINT pk_balance_account PRIMARY KEY (id),
	CONSTRAINT fk_balance_account_part FOREIGN KEY (balance_account_last_part_id) REFERENCES config.balance_account_part(id)
);

CREATE SEQUENCE config.seq_balance_account
	INCREMENT BY 1
	MINVALUE 1
	START 1
	CACHE 1
	NO CYCLE;

-----------------------------------------------------------------------------------------------------------------------------

