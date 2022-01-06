package bhutan.eledger.application.service.ref.rrcocashcounters;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.ref.rrcocashcounters.CreateRefRRCOCashCountersUseCase;
import bhutan.eledger.application.port.out.ref.rrcocashcounters.RefRRCOCashCountersRepositoryPort;
import bhutan.eledger.common.dto.ValidityPeriod;
import bhutan.eledger.domain.ref.rrcocashcounters.RefRRCOCashCounters;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class CreateRefRRCOCashCountersService implements CreateRefRRCOCashCountersUseCase {

    private final RefRRCOCashCountersRepositoryPort refRRCOCashCountersRepositoryPort;

    @Override
    public Long create(CreateRefRRCOCashCountersCommand command) {

        log.trace("Creating RRCO Cash Counters with command: {}", command);

        RefRRCOCashCounters refRRCOCashCounters = mapCommandToRefRRCOCashCounters(command);

        validate(refRRCOCashCounters);

        log.trace("Persisting rrco cash counters: {}", refRRCOCashCounters);

        Long id = refRRCOCashCountersRepositoryPort.create(refRRCOCashCounters);

        log.debug("RRCO Cash Counters with id: {} successfully created.", id);

        return id;
    }

    private RefRRCOCashCounters mapCommandToRefRRCOCashCounters(CreateRefRRCOCashCountersUseCase.CreateRefRRCOCashCountersCommand command) {
        return RefRRCOCashCounters.withoutId(
                command.getCode(),
                ValidityPeriod.of(
                        command.getStartOfValidity(),
                        command.getEndOfValidity()
                ),
                Multilingual.fromMap(command.getDescriptions())
        );
    }

    void validate(RefRRCOCashCounters refRRCOCashCounters) {
        if (refRRCOCashCountersRepositoryPort.isOpenRRCOCashCountersExists(refRRCOCashCounters)) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("Code", "RRCO with code: [" + refRRCOCashCounters.getCode() + "] already exists.")
            );
        }
    }
}
