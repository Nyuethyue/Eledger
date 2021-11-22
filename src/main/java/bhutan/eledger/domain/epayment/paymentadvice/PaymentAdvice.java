package bhutan.eledger.domain.epayment.paymentadvice;


import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data(staticConstructor = "withId")
public class PaymentAdvice {

    private final Long id;
    private final String drn;
    private final LocalDate dueDate;
    private final Period period;
    private final LocalDateTime creationDateTime;
    private final String pan;
    private final PaymentAdviceStatus status;
    private final EpTaxpayer taxpayer;
    private final PaymentAdviceBankInfo bankInfo;
    private final Collection<PayableLine> payableLines;

    public BigDecimal getTotalLiabilityAmount() {
        return payableLines
                .stream()
                .map(PayableLine::getAmount)
//                .map(PaymentLine.GLAccount::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPaidAmountAmount() {
        return payableLines
                .stream()
                .map(PayableLine::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalToBePaidAmountAmount() {
        return payableLines
                .stream()
                .map(PayableLine::getAmountToBePaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Data(staticConstructor = "of")
    public static class Period {
        private final String year;
        private final String segment;
    }

    public static PaymentAdvice withoutId(String drn, LocalDate dueDate, Period period, LocalDateTime creationDateTime, String pan, PaymentAdviceStatus status, EpTaxpayer taxpayer, PaymentAdviceBankInfo bankInfo, Collection<PayableLine> payableLines) {
        return new PaymentAdvice(
                null,
                drn,
                dueDate,
                period,
                creationDateTime,
                pan,
                status,
                taxpayer,
                bankInfo,
                payableLines
        );
    }
}
