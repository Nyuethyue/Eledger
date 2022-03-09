ALTER TABLE ref.tax_period_config DROP CONSTRAINT un_tax_type_code_calendar_year_tax_period_code_transaction_type_id;
ALTER TABLE ref.tax_period_config
    ADD CONSTRAINT un_tt_code_year_tax_period_transaction_type_valid_from_to
        UNIQUE (gl_account_part_full_code, calendar_year, tax_period_code, transaction_type_id, valid_from, valid_to);
