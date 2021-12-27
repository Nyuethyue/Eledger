package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data()
public class ReconciliationUploadRecordInfo {
    private final String depositNumber;

    private final String bankTransactionNumber;
    private final String bankBranchCode;
    private final LocalDate bankProcessingDate;
    private final BigDecimal bankAmount;

    private final LocalDateTime paymentDepositDate;
    private final BigDecimal paymentDepositAmount;
    private final String paymentDepositStatus;
    private final String rowStatus;
}
