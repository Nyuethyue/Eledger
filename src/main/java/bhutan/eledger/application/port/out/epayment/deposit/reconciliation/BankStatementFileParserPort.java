package bhutan.eledger.application.port.out.epayment.deposit.reconciliation;

import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadRecordInfo;

import java.util.List;

public interface BankStatementFileParserPort {
    List<ReconciliationUploadRecordInfo> getStatements(String filePath);
}
