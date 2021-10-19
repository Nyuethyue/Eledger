package bhutan.eledger.application.service.glaccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.persistence.search.SearchValidationUtils;
import bhutan.eledger.application.port.in.config.glaccount.SearchGLAccountUseCase;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountSearchPort;
import bhutan.eledger.configuration.config.glaccount.GLAccountSearchProperties;
import bhutan.eledger.domain.config.glaccount.GLAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
class SearchGLAccountService implements SearchGLAccountUseCase {
    private final GLAccountSearchProperties glAccountSearchProperties;
    private final GLAccountSearchPort glAccountSearchPort;

    @Override
    public SearchResult<GLAccount> search(SearchGLAccountCommand command) {
        log.trace("Search execution started with in command: {}", command);

        SearchValidationUtils.validateSortPropertyAvailability(glAccountSearchProperties.getAvailableSortProperties(), command.getSortProperty());

        var outCommand = mapToSearchCommand(command);

        var searchResult = glAccountSearchPort.search(
                outCommand
        );

        log.debug("Search executed. Result totalCount: {}, totalPages:  {}, out command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), outCommand);

        return searchResult;
    }

    private GLAccountSearchPort.GLAccountSearchCommand mapToSearchCommand(SearchGLAccountCommand command) {
        return new GLAccountSearchPort.GLAccountSearchCommand(
                glAccountSearchProperties.resolvePage(command.getPage()),
                glAccountSearchProperties.resolveSize(command.getSize()),
                glAccountSearchProperties.resolveSortProperty(command.getSortProperty()),
                glAccountSearchProperties.resolveSortDirection(command.getSortDirection()),
                command.getLanguageCode(),
                command.getCode(),
                command.getHead()
        );
    }
}
