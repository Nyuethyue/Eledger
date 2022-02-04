package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.CreateRefOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiod.OpenCloseTaxPeriodRecord;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateRefOpenCloseTaxPeriodService implements CreateRefOpenCloseTaxPeriodUseCase {

    private final RefOpenCloseTaxPeriodRepositoryPort refOpenCloseTaxPeriodRepositoryPort;

    @Override
    public Long create(@Valid CreateOpenCloseTaxPeriodCommand command) {
        log.trace("Creating TaxPeriod with command: {}", command);

        RefOpenCloseTaxPeriodConfig refOpenCloseTaxPeriodConfig = mapCommandToRefOpenCloseTaxPeriodConfig(command);

        log.trace("Persisting open and close taxPeriod: {}", refOpenCloseTaxPeriodConfig);

        Long id = refOpenCloseTaxPeriodRepositoryPort.create(refOpenCloseTaxPeriodConfig);

        log.debug("Open Close taxPeriod with id: {} successfully created.", id);

        return id;
    }

    private RefOpenCloseTaxPeriodConfig mapCommandToRefOpenCloseTaxPeriodConfig(CreateOpenCloseTaxPeriodCommand command) {
        return RefOpenCloseTaxPeriodConfig.withoutId(
                command.getGlAccountFullCode(),
                command.getCalendarYear(),
                command.getTaxPeriodTypeId(),
                command.getTransactionTypeId(),
                command.getYears(),
                command.getMonth(),
                command.getRecords()
                        .stream()
                        .map(record ->
                                OpenCloseTaxPeriodRecord.withoutId(
                                        record.getPeriodId(),
                                        record.getPeriod(),
                                        record.getPeriodStart(),
                                        record.getPeriodEnd()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
