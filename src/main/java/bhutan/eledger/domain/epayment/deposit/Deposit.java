package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Data
public class Deposit {
    private final Long id;
    private final Long paymentModeId;
    private final BigDecimal amount;
    private final LocalDate bankDepositDate;
    private final LocalDate lastPrintedDate;
    private final DepositStatus status;

    private final Collection<DepositReceipt> receipts;
    private final Collection<DenominationCount> denominationCounts;

    public static Deposit withoutId(
            Long paymentModeId,
            BigDecimal amount,
            LocalDate bankDepositDate,
            DepositStatus status,
            Collection<DepositReceipt> receipts,
            Collection<DenominationCount> denominationCounts,
            LocalDate lastPrintedDate
    ) {
        return new Deposit(
                null,
                paymentModeId,
                amount,
                bankDepositDate,
                lastPrintedDate,
                status,
                receipts,
                denominationCounts
        );
    }

    public static Deposit withId(
            Long id,
            Long paymentModeId,
            BigDecimal amount,
            LocalDate bankDepositDate,
            DepositStatus status,
            Collection<DepositReceipt> receipts,
            Collection<DenominationCount> denominationCounts,
            LocalDate lastPrintedDate
    ) {
        return new Deposit(
                id,
                paymentModeId,
                amount,
                bankDepositDate,
                lastPrintedDate,
                status,
                receipts,
                denominationCounts
        );
    }
}
