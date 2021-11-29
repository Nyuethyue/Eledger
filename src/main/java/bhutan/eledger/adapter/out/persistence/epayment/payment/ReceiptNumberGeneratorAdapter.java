package bhutan.eledger.adapter.out.persistence.epayment.payment;

import bhutan.eledger.application.port.out.epayment.payment.ReceiptNumberGeneratorPort;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
class ReceiptNumberGeneratorAdapter implements ReceiptNumberGeneratorPort {
    private static final String RN_PREFIX = "RN";
    private static final DateTimeFormatter RN_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String generate(LocalDate localDate) {

        String formattedDate = RN_DATE_FORMATTER.format(localDate);
        String seqValue = resolveSeqValue();

        return RN_PREFIX + formattedDate + seqValue;
    }

    private String resolveSeqValue() {
        var seqVal = jdbcTemplate.queryForObject("SELECT NEXTVAL('epayment.ep_receipt_number_seq')", Long.class);

        if (seqVal == null) {
            throw new IllegalStateException("The NEXTVAL of sequence 'epayment.ep_receipt_number_seq' returned null.");
        }

        return Strings.padStart(seqVal.toString(), 6, '0');
    }
}
