package bhutan.eledger.application.service.ref.denomination;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.ref.denomination.CreateRefDenominationUseCase;
import bhutan.eledger.application.port.out.ref.denomination.RefDenominationRepositoryPort;
import bhutan.eledger.domain.ref.denomination.RefDenomination;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateRefDenominationService implements CreateRefDenominationUseCase {

    private final RefDenominationRepositoryPort refDenominationRepositoryPort;

    @Override
    public Long create(CreateDenominationCommand command) {
        log.trace("Creating denomination with command: {}", command);

        RefDenomination refDenomination = mapCommandToRefDenomination(command);

        validate(refDenomination);

        log.trace("Persisting denomination: {}", refDenomination);

        Long id = refDenominationRepositoryPort.create(refDenomination);

        log.debug("Branch with id: {} successfully created.", id);

        return id;
    }

    private RefDenomination mapCommandToRefDenomination(CreateDenominationCommand command) {
        return RefDenomination.withoutId(
                command.getValue()
        );
    }

    void validate(RefDenomination refDenomination) {
        if (refDenominationRepositoryPort.existsByValue(refDenomination.getValue())) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("Value", "Denomination with value: [" + refDenomination.getValue() + "] already exists.")
            );
        }
    }
}
