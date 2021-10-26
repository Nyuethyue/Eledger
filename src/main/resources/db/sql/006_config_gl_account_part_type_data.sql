
-----------------------------------------------------------------------------------------------------------------------------

insert into config.gl_account_part_type(id, level, creation_date_time, last_modification_date_time)
values(nextval('config.gl_account_part_type_id_seq'), 1, current_timestamp, current_timestamp)
	, (nextval('config.gl_account_part_type_id_seq'), 2, current_timestamp, current_timestamp)
	, (nextval('config.gl_account_part_type_id_seq'), 3, current_timestamp, current_timestamp)
	, (nextval('config.gl_account_part_type_id_seq'), 4, current_timestamp, current_timestamp)
	, (nextval('config.gl_account_part_type_id_seq'), 5, current_timestamp, current_timestamp)
	, (nextval('config.gl_account_part_type_id_seq'), 6, current_timestamp, current_timestamp)
	, (nextval('config.gl_account_part_type_id_seq'), 7, current_timestamp, current_timestamp);


insert into config.gl_account_part_type_description(id, language_code, value, gl_account_part_type_id)
select nextval('config.gl_account_part_type_description_id_seq') as id
	, 'en' as lang
	, case level
		when 1 then 'Major group'
		when 2 then 'Revenue group'
		when 3 then 'Revenue type'
		when 4 then 'Revenue Sub-type'
		when 5 then 'Functional group'
		when 6 then 'Functional type'
		when 7 then 'Revenue Head/GL Head' end as level_description
	, id as gl_account_part_type_id
from config.gl_account_part_type;

commit;

-----------------------------------------------------------------------------------------------------------------------------