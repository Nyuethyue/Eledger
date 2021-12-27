package bhutan.eledger.application.port.in.epayment.payment.deposit.reconciliation;

import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadRecordInfo;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

public interface BankStatementImportHistoryUseCase {

    Collection<ReconciliationUploadRecordInfo> search(SearchBankStatementsHistoryCommand command);

    @Data
    class SearchBankStatementsHistoryCommand {
        private final LocalDate dateFrom;
        private final LocalDate dateTo;
    }
}
