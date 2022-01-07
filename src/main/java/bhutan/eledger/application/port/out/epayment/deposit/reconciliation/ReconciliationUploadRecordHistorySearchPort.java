package bhutan.eledger.application.port.out.epayment.deposit.reconciliation;

import am.iunetworks.lib.common.persistence.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadRecordInfo;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

public interface ReconciliationUploadRecordHistorySearchPort {

    SearchResult<ReconciliationUploadRecordInfo> search(ReconciliationUploadRecordHistorySearchCommand command);

    @Getter
    @ToString
    class ReconciliationUploadRecordHistorySearchCommand extends AbstractSearchCommand {
        private final LocalDate fromDate;
        private final LocalDate toDate;

        public ReconciliationUploadRecordHistorySearchCommand(int page, int size, String sortProperty, String sortDirection,
                                                              LocalDate fromDate, LocalDate toDate) {
            super(page, size, sortProperty, sortDirection);
            this.fromDate = fromDate;
            this.toDate = toDate;
        }
    }
}
