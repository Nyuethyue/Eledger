package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.persistence.spring.search.SearchResult;
import am.iunetworks.lib.common.persistence.spring.search.SearchValidationUtils;
import bhutan.eledger.application.port.in.config.balanceaccount.SearchBalanceAccountUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountSearchPort;
import bhutan.eledger.configuration.config.balanceaccount.BalanceAccountSearchProperties;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
class SearchBalanceAccountService implements SearchBalanceAccountUseCase {
    private final BalanceAccountSearchProperties balanceAccountSearchProperties;
    private final BalanceAccountSearchPort balanceAccountSearchPort;

    @Override
    public SearchResult<BalanceAccount> search(SearchBalanceAccountCommand command) {
        log.trace("Search execution started with in command: {}", command);

        SearchValidationUtils.validateSortPropertyAvailability(balanceAccountSearchProperties.getAvailableSortProperties(), command.getSortProperty());

        var outCommand = mapToSearchCommand(command);

        var searchResult = balanceAccountSearchPort.search(
                outCommand
        );

        log.debug("Search executed. Result totalCount: {}, totalPages:  {}, out command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), outCommand);

        return searchResult;
    }

    private BalanceAccountSearchPort.BalanceAccountSearchCommand mapToSearchCommand(SearchBalanceAccountCommand command) {
        return new BalanceAccountSearchPort.BalanceAccountSearchCommand(
                balanceAccountSearchProperties.resolvePage(command.getPage()),
                balanceAccountSearchProperties.resolveSize(command.getSize()),
                balanceAccountSearchProperties.resolveSortProperty(command.getSortProperty()),
                balanceAccountSearchProperties.resolveSortDirection(command.getSortDirection()),
                command.getLanguageCode(),
                command.getCode(),
                command.getHead()
        );
    }
}
