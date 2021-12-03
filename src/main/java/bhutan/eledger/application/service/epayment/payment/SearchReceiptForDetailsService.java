package bhutan.eledger.application.service.epayment.payment;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.persistence.search.SearchValidationUtils;
import bhutan.eledger.application.port.in.epayment.payment.SearchReceiptForDetailsUseCase;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptForDetailsSearchPort;
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
public class SearchReceiptForDetailsService implements SearchReceiptForDetailsUseCase {

    private final ReceiptSearchProperties receiptSearchProperties;
    private final ReceiptForDetailsSearchPort receiptForDetailsSearchPort;

    @Override
    public SearchResult<Receipt> search(SearchReceiptForDetailsCommand command) {
        log.trace("Search receipt details started with in command: {}", command);

        SearchValidationUtils.validateSortPropertyAvailability(
                receiptSearchProperties.getAvailableSortProperties(),
                command.getSortProperty()
        );
        var outCommand = makeOutSearchCommand(command);
        var searchResult = receiptForDetailsSearchPort.search(
                outCommand
        );
        log.debug("Search executed. Result totalCount: {}, totalPages: {}, contentCount {}, out port command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), searchResult.getContent().size(), outCommand);
        log.trace("Search result content: {}, out port command: {}", searchResult.getContent(), outCommand);

        return searchResult;
    }

    private ReceiptForDetailsSearchPort.ReceiptForDetailsCommand makeOutSearchCommand(SearchReceiptForDetailsCommand command) {
        return new ReceiptForDetailsSearchPort.ReceiptForDetailsCommand(
                receiptSearchProperties.resolvePage(command.getPage()),
                receiptSearchProperties.resolveSize(command.getSize()),
                receiptSearchProperties.resolveSortProperty(command.getSortProperty()),
                receiptSearchProperties.resolveSortDirection(command.getSortDirection()),
                command.getTpn(),
                command.getReceiptNumber(),
                command.getOtherReferenceNumber(),
                command.getLiabilityFromDate(),
                command.getLiabilityToDate(),
                command.getReceiptFromAmount(),
                command.getReceiptToAmount()


        );
    }
}
