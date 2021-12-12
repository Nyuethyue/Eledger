--  delete redundant gl_account_part
delete from eledger_config.el_gl_account_part_description where gl_account_part_id in (17, 18);
delete from eledger_config.el_gl_account_part where id in (17, 18);