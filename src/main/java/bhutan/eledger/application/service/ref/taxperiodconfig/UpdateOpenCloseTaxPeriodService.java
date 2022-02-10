package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpdateOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertRefOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriod;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpdateOpenCloseTaxPeriodService implements UpdateOpenCloseTaxPeriodUseCase {
    private final RefOpenCloseTaxPeriodRepositoryPort refOpenCloseTaxPeriodRepositoryPort;


    @Override
    public void update(RefOpenCloseTaxPeriod refOpenCloseTaxPeriod, UpsertRefOpenCloseTaxPeriodUseCase.UpsertOpenCloseTaxPeriodCommand command) {
        log.trace("Updating open close tax period {}, by command: {}", refOpenCloseTaxPeriod, command);

        log.trace("Updating open close tax period: {}", refOpenCloseTaxPeriod);

        RefOpenCloseTaxPeriod refOpenCloseTaxPeriodConfig = mapCommandToRefOpenCloseTaxPeriodConfig(refOpenCloseTaxPeriod.getId(),command);

        refOpenCloseTaxPeriodRepositoryPort.update(refOpenCloseTaxPeriodConfig);

        log.debug("Open close tax period id: {} successfully updated.", refOpenCloseTaxPeriod.getId());
    }

    private RefOpenCloseTaxPeriod mapCommandToRefOpenCloseTaxPeriodConfig(Long id,UpsertRefOpenCloseTaxPeriodUseCase.UpsertOpenCloseTaxPeriodCommand  command) {
        return RefOpenCloseTaxPeriod.withId(
                id,
                command.getGlAccountFullCode(),
                command.getCalendarYear(),
                command.getTaxPeriodTypeId(),
                command.getTransactionTypeId(),
                command.getYears(),
                command.getMonth(),
                command.getRecords()
                        .stream()
                        .map(record ->
                                RefOpenCloseTaxPeriodRecord.withoutId(
                                        record.getPeriodId(),
                                        record.getPeriod(),
                                        record.getPeriodOpenDate(),
                                        record.getPeriodCloseDate()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}