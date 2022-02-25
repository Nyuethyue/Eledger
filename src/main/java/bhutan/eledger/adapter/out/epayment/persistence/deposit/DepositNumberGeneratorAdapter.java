package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.application.port.out.epayment.deposit.DepositNumberGeneratorPort;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
class DepositNumberGeneratorAdapter implements DepositNumberGeneratorPort {
    private static final String DN_PREFIX = "DS";
    private static final DateTimeFormatter DN_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String generate(LocalDate localDate) {

        String formattedDate = DN_DATE_FORMATTER.format(localDate);
        String seqValue = resolveSeqValue();

        return DN_PREFIX + formattedDate + seqValue;
    }

    private String resolveSeqValue() {
        var seqVal = jdbcTemplate.queryForObject("SELECT NEXTVAL('epayment.ep_deposit_number_seq')", Long.class);

        if (seqVal == null) {
            throw new IllegalStateException("The NEXTVAL of sequence 'epayment.ep_deposit_number_seq' returned null.");
        }

        return Strings.padStart(seqVal.toString(), 6, '0');
    }
}
