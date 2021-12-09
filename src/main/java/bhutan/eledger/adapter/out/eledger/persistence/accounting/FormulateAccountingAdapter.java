package bhutan.eledger.adapter.out.eledger.persistence.accounting;

import bhutan.eledger.application.port.out.eledger.accounting.formulation.FormulateAccountingPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Map;

@Log4j2
@Component
@RequiredArgsConstructor
class FormulateAccountingAdapter implements FormulateAccountingPort {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void formulate(String tpn, LocalDate formulationDate) {
        log.debug("Formulating data for tpn: {}, at: {}", tpn, formulationDate);

        jdbcTemplate.execute(
                "CALL eledger.sp_process_pac_for_date(:tpn, :formulationDate)",
                Map.of(
                        "tpn", tpn,
                        "formulationDate", formulationDate
                ),
                PreparedStatement::execute
        );
    }
}
