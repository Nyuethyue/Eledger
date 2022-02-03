package bhutan.eledger.application.service.ref.taxperiod;

import bhutan.eledger.application.port.in.ref.taxperiod.UpsertOpenCloseTaxPeriodUseCase;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpsertOpenCloseTaxPeriodService implements UpsertOpenCloseTaxPeriodUseCase {

    @Override
    public Long upsert(@Valid UpsertOpenCloseTaxPeriodCommand command) {
        log.trace("Upserting TaxPeriod with command: {}", command);
        //validate(command);

        RefOpenCloseTaxPeriodConfig refOpenCloseTaxPeriodConfig = mapCommandToRefOpenCloseTaxPeriodConfig(command);

        log.trace("Persisting open and close taxPeriod: {}", refOpenCloseTaxPeriodConfig);

        //Long id = refTaxPeriodRepositoryPort.create(refOpenCloseTaxPeriodConfig);

        //log.debug("Open Close taxPeriod with id: {} successfully created.", 1);

        return null;
    }

    private RefOpenCloseTaxPeriodConfig mapCommandToRefOpenCloseTaxPeriodConfig(UpsertOpenCloseTaxPeriodCommand command) {
        return RefOpenCloseTaxPeriodConfig.withId(
                null,
                command.getTaxTypeCode(),
                command.getCalendarYear(),
                command.getTaxPeriodTypeId(),
                command.getTransactionTypeId(),
                command.getYears(),
                command.getMonth(),
                null
        );
    }
}
