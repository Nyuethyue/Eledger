package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data()
public class ReconciliationUploadRecordInfo {
    private final Long id;
    private final String depositNumber;

    private final String bankTransactionNumber;
    private final String bankBranchCode;
    private final LocalDate bankProcessingDate;
    private final BigDecimal bankAmount;

    private final LocalDateTime depositCreationDateTime;
    private final BigDecimal depositAmount;
    private final String depositStatus;

    private final String recordStatus;
    private final LocalDateTime creationDateTime;
}
