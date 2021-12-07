package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class Deposit {
    private final Long id;
    private final String depositNumber;
    private final Long paymentModeId;
    private final BigDecimal amount;
    private final LocalDate bankDepositDate;
    private final LocalDate lastPrintedDate;
    private final DepositStatus status;

    private final Collection<DepositReceipt> receipts;
    private final Collection<DenominationCount> denominationCounts;

    private final LocalDateTime creationDateTime;

    public static Deposit withoutId(
            String depositNumber,
            Long paymentModeId,
            BigDecimal amount,
            LocalDate bankDepositDate,
            DepositStatus status,
            Collection<DepositReceipt> receipts,
            Collection<DenominationCount> denominationCounts,
            LocalDate lastPrintedDate,
            LocalDateTime creationDateTime
    ) {
        return new Deposit(
                null,
                depositNumber,
                paymentModeId,
                amount,
                bankDepositDate,
                lastPrintedDate,
                status,
                receipts,
                denominationCounts,
                creationDateTime
        );
    }

    public static Deposit withId(
            Long id,
            String depositNumber,
            Long paymentModeId,
            BigDecimal amount,
            LocalDate bankDepositDate,
            DepositStatus status,
            Collection<DepositReceipt> receipts,
            Collection<DenominationCount> denominationCounts,
            LocalDate lastPrintedDate,
            LocalDateTime creationDateTime
    ) {
        return new Deposit(
                id,
                depositNumber,
                paymentModeId,
                amount,
                bankDepositDate,
                lastPrintedDate,
                status,
                receipts,
                denominationCounts,
                creationDateTime
        );
    }
}
