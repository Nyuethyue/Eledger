package bhutan.eledger.application.service.eledger.reporting.glaccountdetails;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.eledger.reporting.glaccountdetails.SearchGLAccountDetailsUseCase;
import bhutan.eledger.application.port.out.eledger.reporting.glaccountdetails.GLAccountDetailsSearchPort;
import bhutan.eledger.domain.eledger.reporting.glaccountdetails.GlAccountDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchGLAccountDetailsService implements SearchGLAccountDetailsUseCase {
    private final GLAccountDetailsSearchPort glAccountDetailsSearchPort;

    @Override
    public SearchResult<GlAccountDetailsDto> search(SearchGLAccountDetailsCommand command) {
        log.trace("Search receipt started with in command: {}", command);

        var outCommand = makeOutSearchCommand(command);

        var searchResult = glAccountDetailsSearchPort.search(
                outCommand
        );

        log.debug("Search executed. Result totalCount: {}, totalPages: {}, contentCount {}, out port command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), searchResult.getContent().size(), outCommand);
        log.trace("Search result content: {}, out port command: {}", searchResult.getContent(), outCommand);

        return searchResult;
    }

    private GLAccountDetailsSearchPort.GLAccountDetailsSearchCommand makeOutSearchCommand(SearchGLAccountDetailsCommand command) {
        return new GLAccountDetailsSearchPort.GLAccountDetailsSearchCommand(
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
