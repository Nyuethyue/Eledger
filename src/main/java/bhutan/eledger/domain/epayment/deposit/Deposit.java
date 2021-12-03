package bhutan.eledger.domain.epayment.deposit;

import bhutan.eledger.domain.epayment.payment.PaymentMode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;


@Data
public class Deposit {
    private final Long id;
    private final PaymentMode paymentMode;
    private final LocalDate lastPrintedDate;
    private final DepositStatus status;

    private final Collection<Long> receipts;
    private final Collection<DenominationCount> denominationCounts;

    public static Deposit withoutId(
            PaymentMode paymentMode,
            LocalDate lastPrintedDate,
            BigDecimal amount,
            DepositStatus status,
            Collection<Long> receipts,
            Collection<DenominationCount> denominationCounts
    ) {
        return new Deposit(
                null,
                paymentMode,
                lastPrintedDate,
                status,
                receipts,
                denominationCounts
        );
    }
}
