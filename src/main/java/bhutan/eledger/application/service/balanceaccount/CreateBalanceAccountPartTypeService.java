package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateBalanceAccountPartTypeService implements CreateBalanceAccountPartTypeUseCase {
    private final BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort;

    @Override
    public Integer create(CreateBalanceAccountPartTypeCommand command) {
        log.trace("Creating balance account part type with command: {}", command);

        BalanceAccountPartType balanceAccountPartType = mapCommandToBalanceAccountPartType(command);

        validate(balanceAccountPartType);

        log.trace("Persisting balance account part type: {}", balanceAccountPartType);

        Integer id = balanceAccountPartTypeRepositoryPort.create(balanceAccountPartType);

        log.debug("Balance account part type with id: {} successfully created.", id);

        return id;
    }

    private BalanceAccountPartType mapCommandToBalanceAccountPartType(CreateBalanceAccountPartTypeCommand command) {
        return BalanceAccountPartType.withoutId(
                command.getLevel(),
                Multilingual.fromMap(command.getDescriptions())
        );
    }

    void validate(BalanceAccountPartType balanceAccountPartType) {
        if (balanceAccountPartTypeRepositoryPort.existByLevel(balanceAccountPartType.getLevel())) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("balanceAccountPartType", "Part with level: [" + balanceAccountPartType.getLevel() + "] already exists.")
            );
        }
    }
}
