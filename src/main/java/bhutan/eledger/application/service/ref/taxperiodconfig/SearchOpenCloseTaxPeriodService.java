package bhutan.eledger.application.service.ref.taxperiodconfig;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.SearchOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchOpenCloseTaxPeriodService implements SearchOpenCloseTaxPeriodUseCase {

    private final RefOpenCloseTaxPeriodRepositoryPort refOpenCloseTaxPeriodRepositoryPort;

    @Override
    public RefOpenCloseTaxPeriod search(@Valid SearchOpenCloseTaxPeriodUseCase.OpenCloseTaxPeriodConfigCommand command) {
        log.trace("Get open close tax period record with command: {}", command);

        return refOpenCloseTaxPeriodRepositoryPort.readByGlFullCodeYearTaxPeriodTransType(
                command.getGlAccountPartFullCode(),
                command.getCalendarYear(),
                command.getTaxPeriodCode(),
                command.getTransactionTypeId()
        ) .orElseThrow(() ->
                new RecordNotFoundException("There is no open close tax period details for the selected parameters.")
        );

    }
}
