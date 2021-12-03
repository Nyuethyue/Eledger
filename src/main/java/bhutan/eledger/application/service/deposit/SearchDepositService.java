package bhutan.eledger.application.service.deposit;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.persistence.search.SearchValidationUtils;
import bhutan.eledger.application.port.in.epayment.payment.SearchReceiptUseCase;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptSearchPort;
import bhutan.eledger.configuration.epayment.payment.ReceiptSearchProperties;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchDepositService implements SearchReceiptUseCase {
    private final ReceiptSearchProperties receiptSearchProperties;
    private final ReceiptSearchPort receiptSearchPort;

    @Override
    public SearchResult<Receipt> search(SearchReceiptCommand command) {
        log.trace("Search receipt started with in command: {}", command);

        SearchValidationUtils.validateSortPropertyAvailability(
                receiptSearchProperties.getAvailableSortProperties(),
                command.getSortProperty()
        );

        var outCommand = makeOutSearchCommand(command);

        var searchResult = receiptSearchPort.search(
                outCommand
        );

        log.debug("Search executed. Result totalCount: {}, totalPages: {}, contentCount {}, out port command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), searchResult.getContent().size(), outCommand);
        log.trace("Search result content: {}, out port command: {}", searchResult.getContent(), outCommand);

        return searchResult;
    }

    private ReceiptSearchPort.ReceiptCommand makeOutSearchCommand(SearchReceiptCommand command) {
        return new ReceiptSearchPort.ReceiptCommand(
                receiptSearchProperties.resolvePage(command.getPage()),
                receiptSearchProperties.resolveSize(command.getSize()),
                receiptSearchProperties.resolveSortProperty(command.getSortProperty()),
                receiptSearchProperties.resolveSortDirection(command.getSortDirection()),
                command.getRefCurrencyId(),
                command.getPaymentMode(),
                command.getBranchCode(),
                command.getGlAccountPartFullCode(),
                command.getReceiptDate()
        );
    }
}
