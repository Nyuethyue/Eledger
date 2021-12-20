package bhutan.eledger.application.service.eledger.reporting.taxpayeraccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.eledger.reporting.taxpayeraccount.SearchTaxpayerAccountUseCase;
import bhutan.eledger.application.port.out.eledger.reporting.taxpayeraccount.TaxpayerAccountSearchPort;
import bhutan.eledger.domain.eledger.reporting.taxpayeraccount.TaxpayerAccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchTaxpayerAccountService implements SearchTaxpayerAccountUseCase {
    private final TaxpayerAccountSearchPort taxpayerAccountSearchPort;

    @Override
    public SearchResult<TaxpayerAccountDto> search(SearchTaxpayerAccountCommand command) {
        log.trace("Search receipt started with in command: {}", command);

        var outCommand = makeOutSearchCommand(command);

        var searchResult = taxpayerAccountSearchPort.search(
                outCommand
        );

        log.debug("Search executed. Result totalCount: {}, totalPages: {}, contentCount {}, out port command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), searchResult.getContent().size(), outCommand);
        log.trace("Search result content: {}, out port command: {}", searchResult.getContent(), outCommand);

        return searchResult;
    }

    private TaxpayerAccountSearchPort.TaxpayerAccountSearchCommand makeOutSearchCommand(SearchTaxpayerAccountCommand command) {
        return new TaxpayerAccountSearchPort.TaxpayerAccountSearchCommand(
                command.getPage() == null ? 0 : command.getPage(),
                command.getSize() == null ? 10 : command.getSize(),
                command.getLanguageCode(),
                command.getTpn(),
                command.getGlAccountPartFullCode(),
                command.getPeriodYear(),
                command.getPeriodSegment(),
                command.getStartTransactionDate(),
                command.getEndTransactionDate()
        );
    }
}
