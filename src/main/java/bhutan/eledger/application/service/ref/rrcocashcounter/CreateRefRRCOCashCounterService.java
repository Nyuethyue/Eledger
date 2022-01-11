package bhutan.eledger.application.service.ref.rrcocashcounter;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.rrcocashcounter.CreateRefRRCOCashCountersUseCase;
import bhutan.eledger.application.port.out.ref.rrcocashcounter.RefRRCOCashCounterRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.rrcocashcounter.RefRRCOCashCounter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class CreateRefRRCOCashCounterService implements CreateRefRRCOCashCountersUseCase {

    private final RefRRCOCashCounterRepositoryPort refRRCOCashCounterRepositoryPort;

    @Override
    public Long create(CreateRefRRCOCashCountersCommand command) {

        log.trace("Creating RRCO Cash Counters with command: {}", command);

        RefRRCOCashCounter refRRCOCashCounter = mapCommandToRefRRCOCashCounters(command);

        validate(refRRCOCashCounter);

        log.trace("Persisting rrco cash counters: {}", refRRCOCashCounter);

        Long id = refRRCOCashCounterRepositoryPort.create(refRRCOCashCounter);

        log.debug("RRCO Cash Counters with id: {} successfully created.", id);

        return id;
    }

    private RefRRCOCashCounter mapCommandToRefRRCOCashCounters(CreateRefRRCOCashCountersUseCase.CreateRefRRCOCashCountersCommand command) {
        return RefRRCOCashCounter.withoutId(
                command.getCode(),
                ValidityPeriod.of(
                        command.getStartOfValidity(),
                        command.getEndOfValidity()
                ),
                Multilingual.fromMap(command.getDescriptions())
        );
    }

    void validate(RefRRCOCashCounter refRRCOCashCounter) {
        if (refRRCOCashCounterRepositoryPort.isOpenRRCOCashCountersExists(refRRCOCashCounter)) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("Code", "RRCO with code: [" + refRRCOCashCounter.getCode() + "] already exists.")
            );
        }
    }
}
