package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data()
public class BankStatementImportReconciliationInfo {
    private String transactionId;
    private String bankBranchCode;
    private String depositNumber;
    private LocalDate paymentDate;
    private BigDecimal amount;
}
