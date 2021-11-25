CREATE OR REPLACE PROCEDURE eledger.sp_update_taxpayer_calculation_date(IN p_taxpayer_id bigint, IN p_calculation_date date)
    LANGUAGE plpgsql
AS
$procedure$

BEGIN
    --raise notice '%', 'start procedure => sp_update_taxpayer_calculation_date';

    UPDATE eledger.el_taxpayer_calc SET calculated_date = p_calculation_date WHERE id = p_taxpayer_id;

    --raise notice '%', 'end procedure => sp_update_taxpayer_calculation_date';

END;
$procedure$
;
