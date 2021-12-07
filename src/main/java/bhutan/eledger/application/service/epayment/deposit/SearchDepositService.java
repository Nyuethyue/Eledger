package bhutan.eledger.application.service.epayment.deposit;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.persistence.search.SearchValidationUtils;
import bhutan.eledger.application.port.in.epayment.deposit.SearchDepositUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositSearchPort;
import bhutan.eledger.configuration.epayment.deposit.DepositSearchProperties;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchDepositService implements SearchDepositUseCase {
    private final DepositSearchProperties depositSearchProperties;
    private final DepositSearchPort depositSearchPort;

    @Override
    public SearchResult<Deposit> search(SearchDepositCommand command) {
        log.trace("Search deposit started with in command: {}", command);

        SearchValidationUtils.validateSortPropertyAvailability(
                depositSearchProperties.getAvailableSortProperties(),
                command.getSortProperty()
        );

        var outCommand = makeOutSearchCommand(command);

        var searchResult = depositSearchPort.search(
                outCommand
        );

        log.debug("Search executed. Result totalCount: {}, totalPages: {}, contentCount {}, out port command: {}", searchResult.getTotalCount(), searchResult.getTotalPages(), searchResult.getContent().size(), outCommand);
        log.trace("Search result content: {}, out port command: {}", searchResult.getContent(), outCommand);

        return searchResult;
    }

    private DepositSearchPort.DepositSearchCommand makeOutSearchCommand(SearchDepositUseCase.SearchDepositCommand command) {
        return new DepositSearchPort.DepositSearchCommand(
                depositSearchProperties.resolvePage(command.getPage()),
                depositSearchProperties.resolveSize(command.getSize()),
                depositSearchProperties.resolveSortProperty(command.getSortProperty()),
                depositSearchProperties.resolveSortDirection(command.getSortDirection()),
                command.getId(),
                command.getFromBankDepositDate(),
                command.getToBankDepositDate()
        );
    }
}
