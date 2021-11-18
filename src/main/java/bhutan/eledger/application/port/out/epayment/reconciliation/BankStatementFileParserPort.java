package bhutan.eledger.application.port.out.epayment.reconciliation;

import bhutan.eledger.domain.epayment.BankStatementImportReconciliationInfo;

import java.util.List;

public interface BankStatementFileParserPort {
    List<BankStatementImportReconciliationInfo> getStatements(String filePath);
}
