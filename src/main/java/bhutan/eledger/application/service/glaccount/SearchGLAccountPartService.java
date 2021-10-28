package bhutan.eledger.application.service.glaccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.persistence.search.SearchValidationUtils;
import bhutan.eledger.application.port.in.config.glaccount.SearchGLAccountPartUseCase;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartSearchPort;
import bhutan.eledger.configuration.config.glaccount.GLAccountPartSearchProperties;
import bhutan.eledger.domain.config.glaccount.GLAccountPart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchGLAccountPartService implements SearchGLAccountPartUseCase {
    private final GLAccountPartSearchProperties glAccountPartSearchProperties;
    private final GLAccountPartSearchPort glAccountPartSearchPort;

    @Override
    public SearchResult<GLAccountPart> search(SearchGLAccountPartCommand command) {
        log.trace("Search execution started with in command: {}", command);

        SearchValidationUtils.validateSortPropertyAvailability(glAccountPartSearchProperties.getAvailableSortProperties(), command.getSortProperty());

        var outCommand = mapToSearchCommand(command);

        var searchResult = glAccountPartSearchPort.search(
                outCommand
        );

        log.debug("Search executed. Result totalCount: {}, totalPages:  {}, out command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), outCommand);

        return searchResult;
    }

    private GLAccountPartSearchPort.GLAccountPartSearchCommand mapToSearchCommand(SearchGLAccountPartCommand command) {
        return new GLAccountPartSearchPort.GLAccountPartSearchCommand(
                glAccountPartSearchProperties.resolvePage(command.getPage()),
                glAccountPartSearchProperties.resolveSize(command.getSize()),
                glAccountPartSearchProperties.resolveSortProperty(command.getSortProperty()),
                glAccountPartSearchProperties.resolveSortDirection(command.getSortDirection()),
                command.getLanguageCode(),
                command.getCode(),
                command.getHead(),
                command.getPartTypeId()
        );
    }
}
