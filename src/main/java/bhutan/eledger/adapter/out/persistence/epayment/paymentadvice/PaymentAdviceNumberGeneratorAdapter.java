package bhutan.eledger.adapter.out.persistence.epayment.paymentadvice;

import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceNumberGeneratorPort;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
class PaymentAdviceNumberGeneratorAdapter implements PaymentAdviceNumberGeneratorPort {
    private static final String PA_PREFIX = "PA";
    private static final DateTimeFormatter PA_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String generate(LocalDate localDate) {

        String formattedDate = PA_DATE_FORMATTER.format(localDate);
        String seqValue = resolveSeqValue();

        return PA_PREFIX + formattedDate + seqValue;
    }

    private String resolveSeqValue() {
        var seqVal = jdbcTemplate.queryForObject("SELECT NEXTVAL('epayment.ep_payment_advice_number_seq')", Long.class);

        if (seqVal == null) {
            throw new IllegalStateException("The NEXTVAL of sequence 'epayment.ep_payment_advice_number_seq' returned 0.");
        }

        return Strings.padStart(seqVal.toString(), 6, '0');
    }
}
