package bhutan.eledger.application.service.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.persistence.search.SearchValidationUtils;
import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdviceSspPort;
import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdviceSspUseCase;
import bhutan.eledger.configuration.epayment.paymentadvice.SearchPaymentAdviceSspProperties;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchPaymentAdviceSspService implements SearchPaymentAdviceSspUseCase {
    private final SearchPaymentAdviceSspProperties searchPaymentAdviceSspProperties;
    private final SearchPaymentAdviceSspPort searchPaymentAdviceSspPort;

    @Override
    public SearchResult<PaymentAdvice> search(SearchPaymentAdviseSspCommand command) {
        log.trace("Search execution started with in command: {}", command);

        SearchValidationUtils.validateSortPropertyAvailability(searchPaymentAdviceSspProperties.getAvailableSortProperties(), command.getSortProperty());

        var outCommand = mapToSearchCommand(command);

        var searchResult = searchPaymentAdviceSspPort.search(outCommand);

        log.debug("Search executed. Result totalCount: {}, totalPages: {}, contentCount {}, out port command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), searchResult.getContent().size(), outCommand);
        log.trace("Search result content: {}, out port command: {}", searchResult.getContent(), outCommand);

        return searchResult;
    }


    private SearchPaymentAdviceSspPort.PaymentAdviseSspSearchCommand mapToSearchCommand(SearchPaymentAdviseSspCommand command) {
        return new SearchPaymentAdviceSspPort.PaymentAdviseSspSearchCommand(
                searchPaymentAdviceSspProperties.resolvePage(command.getPage()),
                searchPaymentAdviceSspProperties.resolveSize(command.getSize()),
                searchPaymentAdviceSspProperties.resolveSortProperty(command.getSortProperty()),
                searchPaymentAdviceSspProperties.resolveSortDirection(command.getSortDirection()),
                command.getTpn(),
                command.getPan(),
                command.getStatus(),
                command.getCreationDateTimeFrom(),
                command.getCreationDateTimeTo(),
                command.getTotalToBePaidAmountFrom(),
                command.getTotalToBePaidAmountTo()
        );
    }


}
