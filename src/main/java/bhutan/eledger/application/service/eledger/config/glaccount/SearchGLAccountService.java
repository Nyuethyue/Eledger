package bhutan.eledger.application.service.eledger.config.glaccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.persistence.search.SearchValidationUtils;
import bhutan.eledger.application.port.in.eledger.config.glaccount.SearchGLAccountUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountSearchPort;
import bhutan.eledger.configuration.eledger.config.glaccount.GLAccountSearchProperties;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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

        log.debug("Search executed. Result totalCount: {}, totalPages: {}, contentCount {}, out port command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), searchResult.getContent().size(), outCommand);
        log.trace("Search result content: {}, out port command: {}", searchResult.getContent(), outCommand);

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
