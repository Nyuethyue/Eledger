-- create schema "config"
CREATE SCHEMA config
AUTHORIZATION bu_eledger;

CREATE TABLE config.bal_account_attributes (
	bal_account_attribute_id serial NOT NULL,
	description varchar(255) NOT NULL,
	attr_level int,
	CONSTRAINT bal_account_attributes_pkey PRIMARY KEY (bal_account_attribute_id)
);

CREATE TABLE config.bal_account_attribute_values (
	bal_account_attribute_value_id serial NOT NULL,
	parent_bal_account_attribute_value_id int,
	description varchar(255) NOT NULL,
	code varchar(4) NOT NULL,
	attr_level int,
	CONSTRAINT bal_account_attribute_values_pkey PRIMARY KEY (bal_account_attribute_value_id)
);

CREATE TABLE config.bal_accounts (
	bal_account_id serial NOT NULL,
	bal_account varchar(50) NOT NULL,
	description varchar(255) NULL,
	is_debit bool,
	CONSTRAINT bal_accounts_pkey PRIMARY KEY (bal_account_id)
);


