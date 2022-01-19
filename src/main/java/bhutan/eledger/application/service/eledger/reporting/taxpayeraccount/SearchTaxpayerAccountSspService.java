package bhutan.eledger.application.service.eledger.reporting.taxpayeraccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.eledger.reporting.taxpayeraccount.SearchTaxpayerAccountSspUseCase;
import bhutan.eledger.application.port.out.eledger.reporting.taxpayeraccount.TaxpayerAccountSspSearchPort;
import bhutan.eledger.domain.eledger.reporting.taxpayeraccount.TaxpayerAccountSspDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchTaxpayerAccountSspService implements SearchTaxpayerAccountSspUseCase {
    private final TaxpayerAccountSspSearchPort taxpayerAccountSspSearchPort;

    @Override
    public SearchResult<TaxpayerAccountSspDto> search(SearchTaxpayerAccountSspCommand command) {
        log.trace("Search taxpayer account ssp report started with in command: {}", command);

        var outCommand = makeOutSearchCommand(command);

        var searchResult = taxpayerAccountSspSearchPort.search(
                outCommand
        );

        log.debug("Search executed. Result totalCount: {}, totalPages: {}, contentCount {}, out port command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), searchResult.getContent().size(), outCommand);
        log.trace("Search result content: {}, out port command: {}", searchResult.getContent(), outCommand);

        return searchResult;
    }

    private TaxpayerAccountSspSearchPort.TaxpayerAccountSspSearchCommand makeOutSearchCommand(SearchTaxpayerAccountSspCommand command) {
        return new TaxpayerAccountSspSearchPort.TaxpayerAccountSspSearchCommand(
                command.getLanguageCode(),
                command.getTpn(),
                command.getGlAccountPartFullCode(),
                command.getPeriodYear(),
                command.getPeriodSegment()
        );
    }
}
