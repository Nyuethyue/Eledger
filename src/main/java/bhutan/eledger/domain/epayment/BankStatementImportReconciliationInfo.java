package bhutan.eledger.domain.epayment;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data(staticConstructor = "of")
public class BankStatementImportReconciliationInfo {

    private final String transactionId;
    private final String bankId;
    private final LocalDate paymentDate;
    private final BigDecimal amount;
}
