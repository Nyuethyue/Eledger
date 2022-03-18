ALTER TABLE ref."agency_gl_account" ADD "start_of_validity" date  NOT NULL DEFAULT CURRENT_DATE;
ALTER TABLE ref."agency_gl_account" ADD "end_of_validity"  date  NULL;