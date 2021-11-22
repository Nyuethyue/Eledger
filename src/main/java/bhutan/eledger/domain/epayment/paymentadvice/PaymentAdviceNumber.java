package bhutan.eledger.domain.epayment.paymentadvice;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class PaymentAdviceNumber {
    private static final String KEY_FIRST_PART = "PA";

    private final String firstPart;
    private final LocalDate generationDate;
    private final Long seqValue;
    private final String generatedKey;

    public static PaymentAdviceNumber of(LocalDate generationDate, Long seqValue) {
        return new PaymentAdviceNumber(
                KEY_FIRST_PART,
                generationDate,
                seqValue,
                KEY_FIRST_PART + DateTimeFormatter.ofPattern("yyMMdd").format(generationDate) + seqValue
        );
    }

    public static PaymentAdviceNumber of(LocalDate generationDate, Long seqValue, String key) {
        return new PaymentAdviceNumber(
                KEY_FIRST_PART,
                generationDate,
                seqValue,
                key
        );
    }

    public String getKey() {
        return generatedKey;
    }
}
