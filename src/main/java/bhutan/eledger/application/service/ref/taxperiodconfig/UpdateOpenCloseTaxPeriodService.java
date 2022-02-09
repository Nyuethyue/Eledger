package bhutan.eledger.application.service.ref.taxperiodconfig;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpdateOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertRefOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefOpenCloseTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriod;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpdateOpenCloseTaxPeriodService implements UpdateOpenCloseTaxPeriodUseCase {
    private final RefOpenCloseTaxPeriodRepositoryPort refOpenCloseTaxPeriodRepositoryPort;

    @Override
    public void update(Long id, UpsertRefOpenCloseTaxPeriodUseCase.UpsertOpenCloseTaxPeriodCommand command) {
        update(
                refOpenCloseTaxPeriodRepositoryPort.readById(id).orElseThrow(() -> new RecordNotFoundException("Open close tax period by id: [" + id + "] not found.")),
                command
        );
    }

    @Override
    public void update(RefOpenCloseTaxPeriod refOpenCloseTaxPeriod, UpsertRefOpenCloseTaxPeriodUseCase.UpsertOpenCloseTaxPeriodCommand command) {
        log.trace("Updating open close tax period {}, by command: {}", refOpenCloseTaxPeriod, command);

        log.trace("Updating open close tax period: {}", refOpenCloseTaxPeriod);

        command.getRecords()
                .stream()
                .map(recordCommand ->
                        RefOpenCloseTaxPeriodRecord.withoutId(
                            recordCommand.getPeriodId(),
                            recordCommand.getPeriod(),
                            recordCommand.getPeriodOpenDate(),
                            recordCommand.getPeriodCloseDate()
                        )
                )
                .forEach(refOpenCloseTaxPeriod::upsertOpenCloseTaxPeriodRecord);
        refOpenCloseTaxPeriodRepositoryPort.update(refOpenCloseTaxPeriod);

        log.debug("Open close tax period id: {} successfully updated.", refOpenCloseTaxPeriod.getId());
    }
}