package bhutan.eledger.application.port.in.epayment.reconciliation;

import bhutan.eledger.domain.epayment.BankStatementImportReconciliationInfo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public interface BankStatementImportUseCase {

    BankStatementImportReconciliationInfo importStatements(ImportBankStatementsCommand command);

    @Data
    class ImportBankStatementsCommand {
        @NotNull
        @NotEmpty
        private final String tpn;
        @NotNull
        @NotEmpty
        private final String excelFilePath;
    }
}
