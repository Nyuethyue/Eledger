package bhutan.eledger.domain.epayment.paymentadvice;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data(staticConstructor = "withId")
public class PaymentAdvice {

    private final Long id;
    private final String drn;
    private final String tpn;
    private final LocalDate dueDate;
    private final Period period;
    private final LocalDateTime creationDateTime;
    private final String pan;
    private final PaymentAdviceStatus status;
    private final PaymentAdviceBankInfo bankInfo;
    private final Collection<PaymentLine> paymentLines;

    public BigDecimal getTotalLiabilityAmount() {
        return paymentLines
                .stream()
                .map(PaymentLine::getAmount)
//                .map(PaymentLine.GLAccount::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPaidAmountAmount() {
        return paymentLines
                .stream()
                .map(PaymentLine::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalToBePaidAmountAmount() {
        return paymentLines
                .stream()
                .map(PaymentLine::getAmountToBePaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Data(staticConstructor = "of")
    public static class Period {
        private final LocalDate start;
        private final LocalDate end;
    }

    public static PaymentAdvice withoutId(String drn, String tpn, LocalDate dueDate, Period period, LocalDateTime creationDateTime, String pan, PaymentAdviceStatus status, PaymentAdviceBankInfo bankInfo, Collection<PaymentLine> paymentLines) {
        return new PaymentAdvice(
                null,
                drn,
                tpn,
                dueDate,
                period,
                creationDateTime,
                pan,
                status,
                bankInfo,
                paymentLines
        );
    }
}
