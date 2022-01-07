package bhutan.eledger.application.service.epayment.deposit;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.persistence.search.SearchValidationUtils;
import bhutan.eledger.application.port.in.epayment.deposit.SearchReconciliationUploadHistoryUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.reconciliation.ReconciliationUploadRecordHistorySearchPort;
import bhutan.eledger.configuration.epayment.deposit.ReconciliationRecordHistorySearchProperties;
import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadRecordInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchReconciliationHistoryService implements SearchReconciliationUploadHistoryUseCase {
    private final ReconciliationRecordHistorySearchProperties reconciliationRecordHistorySearchProperties;
    private final ReconciliationUploadRecordHistorySearchPort reconciliationUploadRecordSearchPort;

    @Override
    public SearchResult<ReconciliationUploadRecordInfo> search(SearchReconciliationUploadRecordCommand command) {
        log.trace("Search reconciliation record history started with in command: {}", command);

        SearchValidationUtils.validateSortPropertyAvailability(
                reconciliationRecordHistorySearchProperties.getAvailableSortProperties(),
                command.getSortProperty()
        );

        var outCommand = makeOutSearchCommand(command);

        var searchResult = reconciliationUploadRecordSearchPort.search(outCommand);

        log.debug("Search executed. Result totalCount: {}, totalPages: {}, contentCount {}, out port command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), searchResult.getContent().size(), outCommand);
        log.trace("Search result content: {}, out port command: {}", searchResult.getContent(), outCommand);

        return searchResult;
    }

    private ReconciliationUploadRecordHistorySearchPort.ReconciliationUploadRecordHistorySearchCommand makeOutSearchCommand(SearchReconciliationUploadRecordCommand command) {
        return new ReconciliationUploadRecordHistorySearchPort.ReconciliationUploadRecordHistorySearchCommand(
                reconciliationRecordHistorySearchProperties.resolvePage(command.getPage()),
                reconciliationRecordHistorySearchProperties.resolveSize(command.getSize()),
                reconciliationRecordHistorySearchProperties.resolveSortProperty(command.getSortProperty()),
                reconciliationRecordHistorySearchProperties.resolveSortDirection(command.getSortDirection()),
                command.getFromDate(),
                command.getToDate()
        );
    }
}
