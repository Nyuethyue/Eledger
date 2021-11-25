package bhutan.eledger.domain.epayment;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data()
public class BankStatementImportReconciliationInfo {
    private String transactionId;
    private String bankId;
    private String refNo;
    private LocalDate paymentDate;
    private BigDecimal amount;
}
