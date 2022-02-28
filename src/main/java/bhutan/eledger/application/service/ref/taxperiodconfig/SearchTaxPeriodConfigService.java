package bhutan.eledger.application.service.ref.taxperiodconfig;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.SearchTaxPeriodConfigUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchTaxPeriodConfigService implements SearchTaxPeriodConfigUseCase {
    private final RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;

    @Override
    public SearchResult<RefTaxPeriodConfig> search(@Valid SearchTaxPeriodConfigCommand command) {
        log.trace("Searching tax period record with command: {}", command);
        return refTaxPeriodRepositoryPort.search(
                command.getTaxTypeCode(),
                command.getCalendarYear(),
                command.getTaxPeriodCode(),
                command.getTransactionTypeId()
        );
    }
}
