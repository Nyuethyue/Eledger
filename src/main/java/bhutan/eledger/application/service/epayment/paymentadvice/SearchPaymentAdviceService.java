package bhutan.eledger.application.service.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.persistence.search.SearchValidationUtils;
import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;

import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdvicePort;
import bhutan.eledger.configuration.epayment.config.paymentadvice.SearchPaymentAdviceProperties;
import bhutan.eledger.domain.epayment.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdviceUseCase;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchPaymentAdviceService implements SearchPaymentAdviceUseCase {
    private final SearchPaymentAdviceProperties searchPaymentAdviceProperties;
    private final SearchPaymentAdvicePort searchPaymentAdvicePort;

    @Override
    public SearchResult<PaymentAdvice> search(SearchPaymentAdviseCommand command) {
        log.trace("Search execution started with in command: {}", command);

        SearchValidationUtils.validateSortPropertyAvailability(searchPaymentAdviceProperties.getAvailableSortProperties(), command.getSortProperty());

        if ((null == command.getTpn() && null == command.getPan()) ||
                (null != command.getTpn() && null != command.getPan())) {
            throw new ViolationException((new ValidationError()).addViolation("invalidSearchParams", "Exactly one search field must be provided [TPN or PAN]"));
        }
        var outCommand = mapToSearchCommand(command);

        var searchResult = searchPaymentAdvicePort.search(outCommand);

        log.debug("Search executed. Result totalCount: {}, totalPages: {}, contentCount {}, out port command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), searchResult.getContent().size(), outCommand);
        log.trace("Search result content: {}, out port command: {}", searchResult.getContent(), outCommand);

        return searchResult;
    }

    private SearchPaymentAdvicePort.PaymentAdviseSearchCommand mapToSearchCommand(SearchPaymentAdviseCommand command) {
        return new SearchPaymentAdvicePort.PaymentAdviseSearchCommand(
                searchPaymentAdviceProperties.resolvePage(command.getPage()),
                searchPaymentAdviceProperties.resolveSize(command.getSize()),
                searchPaymentAdviceProperties.resolveSortProperty(command.getSortProperty()),
                searchPaymentAdviceProperties.resolveSortDirection(command.getSortDirection()),
                command.getTaxpayerType(),
                command.getTpn(),
                command.getPan()
        );
    }
}
