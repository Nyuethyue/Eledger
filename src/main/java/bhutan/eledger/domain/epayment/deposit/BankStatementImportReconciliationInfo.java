package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data()
public class BankStatementImportReconciliationInfo {
    // TRN_REF_NO	BANK_CODES	REF_NO	TXN_DT	TXN_AMT
    private String transactionId;
    private String bankId;
    private String depositNumber;
    private LocalDate paymentDate;
    private BigDecimal amount;
}
