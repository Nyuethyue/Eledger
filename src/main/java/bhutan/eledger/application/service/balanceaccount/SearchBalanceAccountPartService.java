package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.persistence.search.SearchValidationUtils;
import bhutan.eledger.application.port.in.config.balanceaccount.SearchBalanceAccountPartUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartSearchPort;
import bhutan.eledger.configuration.config.balanceaccount.BalanceAccountPartSearchProperties;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
class SearchBalanceAccountPartService implements SearchBalanceAccountPartUseCase {
    private final BalanceAccountPartSearchProperties balanceAccountPartSearchProperties;
    private final BalanceAccountPartSearchPort balanceAccountPartSearchPort;

    @Override
    public SearchResult<BalanceAccountPart> search(SearchBalanceAccountPartCommand command) {
        log.trace("Search execution started with in command: {}", command);

        SearchValidationUtils.validateSortPropertyAvailability(balanceAccountPartSearchProperties.getAvailableSortProperties(), command.getSortProperty());

        var outCommand = mapToSearchCommand(command);

        var searchResult = balanceAccountPartSearchPort.search(
                outCommand
        );

        log.debug("Search executed. Result totalCount: {}, totalPages:  {}, out command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), outCommand);

        return searchResult;
    }

    private BalanceAccountPartSearchPort.BalanceAccountPartSearchCommand mapToSearchCommand(SearchBalanceAccountPartCommand command) {
        return new BalanceAccountPartSearchPort.BalanceAccountPartSearchCommand(
                balanceAccountPartSearchProperties.resolvePage(command.getPage()),
                balanceAccountPartSearchProperties.resolveSize(command.getSize()),
                balanceAccountPartSearchProperties.resolveSortProperty(command.getSortProperty()),
                balanceAccountPartSearchProperties.resolveSortDirection(command.getSortDirection()),
                command.getLanguageCode(),
                command.getCode(),
                command.getHead(),
                command.getPartTypeId()
        );
    }
}
