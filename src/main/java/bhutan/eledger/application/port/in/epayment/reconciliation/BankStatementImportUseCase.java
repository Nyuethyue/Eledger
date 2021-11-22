package bhutan.eledger.application.port.in.epayment.reconciliation;

import bhutan.eledger.domain.epayment.BankStatementImportReconciliationInfo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface BankStatementImportUseCase {
    List<BankStatementImportReconciliationInfo> importStatements(ImportBankStatementsCommand command);

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
