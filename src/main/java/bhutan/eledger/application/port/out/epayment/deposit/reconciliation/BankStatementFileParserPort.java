package bhutan.eledger.application.port.out.epayment.deposit.reconciliation;

import bhutan.eledger.domain.epayment.deposit.BankStatementImportReconciliationInfo;

import java.util.List;

public interface BankStatementFileParserPort {
    List<BankStatementImportReconciliationInfo> getStatements(String filePath);
}
