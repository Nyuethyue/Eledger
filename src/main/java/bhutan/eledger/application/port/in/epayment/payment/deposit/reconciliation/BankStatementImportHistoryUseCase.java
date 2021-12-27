package bhutan.eledger.application.port.in.epayment.payment.deposit.reconciliation;

import bhutan.eledger.domain.epayment.deposit.BankReconciliationUploadInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public interface BankStatementImportHistoryUseCase {

    BankReconciliationUploadInfo search(SearchBankStatementsHistoryCommand command);

    @Data
    class SearchBankStatementsHistoryCommand {
        private final LocalDate dateFrom;
        private final LocalDate dateTo;
    }

    @Data
    class ReconciliationUploadFileInfo {
        private final Long uploadId;
        private final String filePath;
        private final LocalDate uploadDate;
        private final Collection<ReconciliationUploadRecordInfo> records;
    }

    @Data
    class ReconciliationUploadRecordInfo {
        private final String depositNumber;

        private final String bankTransactionNumber;
        private final String bankBranchCode;
        private final LocalDate bankProcessingDate;
        private final BigDecimal bankAmount;

        private final LocalDateTime paymentDepositDate;
        private final BigDecimal paymentDepositAmount;
        private final String paymentDepositStatus;
        private final String rowStatus;
    }
}
