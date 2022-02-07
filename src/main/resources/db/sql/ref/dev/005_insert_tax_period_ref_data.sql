
--------------------------------------------------------------------------------------------------------------

insert into ref.tax_period(id, code)
values(NEXTVAL('ref.tax_period_id_seq'), 'FORTNIGHTLY')
	, (NEXTVAL('ref.tax_period_id_seq'), 'MONTHLY')
	, (NEXTVAL('ref.tax_period_id_seq'), 'QUARTERLY')
	, (NEXTVAL('ref.tax_period_id_seq'), 'HALF_YEARLY')
	, (NEXTVAL('ref.tax_period_id_seq'), 'YEARLY')
	;

--------------------------------------------------------------------------------------------------------------

insert into ref.tax_period_description(id, language_code, value, tax_period_id)
values(NEXTVAL('ref.tax_period_description_id_seq'), 'en', 'Fortnightly', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_description_id_seq'), 'en', 'Monthly', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_description_id_seq'), 'en', 'Quarterly', (select id from ref.tax_period where code = 'QUARTERLY'))
	, (NEXTVAL('ref.tax_period_description_id_seq'), 'en', 'Half yearly', (select id from ref.tax_period where code = 'HALF_YEARLY'))
	, (NEXTVAL('ref.tax_period_description_id_seq'), 'en', 'Yearly', (select id from ref.tax_period where code = 'YEARLY'))
	;

--------------------------------------------------------------------------------------------------------------

insert into ref.tax_period_segment(id, code, tax_period_id)
values
--	MONTHLY
	  (NEXTVAL('ref.tax_period_segment_id_seq'), '1', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '2', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '3', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '4', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '5', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '6', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '7', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '8', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '9', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '10', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '11', (select id from ref.tax_period where code = 'MONTHLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '12', (select id from ref.tax_period where code = 'MONTHLY'))

-- QUARTERLY
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '21', (select id from ref.tax_period where code = 'QUARTERLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '22', (select id from ref.tax_period where code = 'QUARTERLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '23', (select id from ref.tax_period where code = 'QUARTERLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '24', (select id from ref.tax_period where code = 'QUARTERLY'))

-- FORTNIGHTLY
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '31', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '32', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '33', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '34', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '35', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '36', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '37', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '38', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '39', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '40', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '41', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '42', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '43', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '44', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '45', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '46', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '47', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '48', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '49', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '50', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '51', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '52', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '53', (select id from ref.tax_period where code = 'FORTNIGHTLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '54', (select id from ref.tax_period where code = 'FORTNIGHTLY'))

--	HALF_YEARLY
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '60', (select id from ref.tax_period where code = 'HALF_YEARLY'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '61', (select id from ref.tax_period where code = 'HALF_YEARLY'))

--	YEARLY
	, (NEXTVAL('ref.tax_period_segment_id_seq'), '70', (select id from ref.tax_period where code = 'YEARLY'))
	;

--------------------------------------------------------------------------------------------------------------

insert into ref.tax_period_segment_description(id, language_code, value, tax_period_segment_id)
values
--	MONTHLY
	  (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'January', (select id from ref.tax_period_segment where code = '1'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'February', (select id from ref.tax_period_segment where code = '2'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'March', (select id from ref.tax_period_segment where code = '3'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'April', (select id from ref.tax_period_segment where code = '4'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'May', (select id from ref.tax_period_segment where code = '5'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'June', (select id from ref.tax_period_segment where code = '6'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'July', (select id from ref.tax_period_segment where code = '7'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'August', (select id from ref.tax_period_segment where code = '8'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'September', (select id from ref.tax_period_segment where code = '9'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'October', (select id from ref.tax_period_segment where code = '10'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'November', (select id from ref.tax_period_segment where code = '11'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'December', (select id from ref.tax_period_segment where code = '12'))

-- QUARTERLY
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', '1st Quarter', (select id from ref.tax_period_segment where code = '21'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', '2nd Quarter', (select id from ref.tax_period_segment where code = '22'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', '3rd Quarter', (select id from ref.tax_period_segment where code = '23'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', '4th Quarter', (select id from ref.tax_period_segment where code = '24'))

-- FORTNIGHTLY
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 1', (select id from ref.tax_period_segment where code = '31'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 2', (select id from ref.tax_period_segment where code = '32'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 3', (select id from ref.tax_period_segment where code = '33'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 4', (select id from ref.tax_period_segment where code = '34'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 5', (select id from ref.tax_period_segment where code = '35'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 6', (select id from ref.tax_period_segment where code = '36'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 7', (select id from ref.tax_period_segment where code = '37'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 8', (select id from ref.tax_period_segment where code = '38'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 9', (select id from ref.tax_period_segment where code = '39'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 10', (select id from ref.tax_period_segment where code = '40'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 11', (select id from ref.tax_period_segment where code = '41'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 12', (select id from ref.tax_period_segment where code = '42'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 13', (select id from ref.tax_period_segment where code = '43'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 14', (select id from ref.tax_period_segment where code = '44'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 15', (select id from ref.tax_period_segment where code = '45'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 16', (select id from ref.tax_period_segment where code = '46'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 17', (select id from ref.tax_period_segment where code = '47'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 18', (select id from ref.tax_period_segment where code = '48'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 19', (select id from ref.tax_period_segment where code = '49'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 20', (select id from ref.tax_period_segment where code = '50'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 21', (select id from ref.tax_period_segment where code = '51'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 22', (select id from ref.tax_period_segment where code = '52'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 23', (select id from ref.tax_period_segment where code = '53'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Fortnight 24', (select id from ref.tax_period_segment where code = '54'))

--	HALF_YEARLY
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', '1st semi year', (select id from ref.tax_period_segment where code = '60'))
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', '2nd semi year', (select id from ref.tax_period_segment where code = '61'))

--	YEARLY
	, (NEXTVAL('ref.tax_period_segment_id_seq'), 'en', 'Year', (select id from ref.tax_period_segment where code = '70'))
	;

--------------------------------------------------------------------------------------------------------------
