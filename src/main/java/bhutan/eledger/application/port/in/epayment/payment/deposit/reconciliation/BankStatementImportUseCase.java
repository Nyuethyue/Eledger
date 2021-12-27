package bhutan.eledger.application.port.in.epayment.payment.deposit.reconciliation;

import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadRecordInfo;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface BankStatementImportUseCase {

    List<ReconciliationUploadRecordInfo> importStatements(ImportBankStatementsCommand command);

    @Data
    class ImportBankStatementsCommand {
        @NotNull
        @NotEmpty
        private final String excelFilePath;
    }
}
