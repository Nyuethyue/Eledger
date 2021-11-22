package bhutan.eledger.application.port.out.epayment.reconciliation;

import bhutan.eledger.application.port.in.epayment.reconciliation.BankStatementImportUseCase;
import bhutan.eledger.domain.epayment.BankStatementImportReconciliationInfo;

import java.util.List;

public interface BankStatementFileParserPort {
    List<BankStatementImportReconciliationInfo> getStatements(BankStatementImportUseCase.ImportBankStatementsCommand command);
}
