package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartLevelRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateBalanceAccountPartService implements CreateBalanceAccountPartUseCase {
    private final BalanceAccountPartLevelRepositoryPort balanceAccountPartLevelRepositoryPort;
    private final BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;

    @Override
    public Collection<BalanceAccountPart> create(CreateBalanceAccountPartCommand command) {

        log.trace("Creating balance account part with command: {}", command);

        validate(command);

        var balanceAccountParts = mapCommandToBalanceAccountParts(command);

        log.trace("Persisting balance account parts: {}", balanceAccountParts);

        var persistedBalanceAccountParts = balanceAccountPartRepositoryPort.create(balanceAccountParts);

        log.debug("Balance account part level with ids: {} successfully created.", () -> persistedBalanceAccountParts.stream().map(BalanceAccountPart::getCode).collect(Collectors.toUnmodifiableList()));


        return persistedBalanceAccountParts;
    }

    private void validate(CreateBalanceAccountPartCommand command) {

        checkLevelExistence(command);
        checkPartsExistence(command);
    }

    private void checkLevelExistence(CreateBalanceAccountPartCommand command) {
        if (balanceAccountPartLevelRepositoryPort.existById(command.getBalanceAccountPartLevelId())) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("balanceAccountPartLevelId", "Level: [" + command.getBalanceAccountPartLevelId() + "] doesn't exists.")
            );
        }
    }

    private void checkPartsExistence(CreateBalanceAccountPartCommand command) {
        Collection<String> balanceAccountPartCodes = command.getBalanceAccountParts()
                .stream()
                .map(BalanceAccountPartCommand::getCode)
                .collect(Collectors.toUnmodifiableList());

        boolean isAnyBalanceAccountExistsForLevel = balanceAccountPartRepositoryPort.existByParentIdAndCodeInList(
                command.getParentId(),
                balanceAccountPartCodes
        );

        if (isAnyBalanceAccountExistsForLevel) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("balanceAccountPart", "One or more balance account part already exists. Codes: [" + balanceAccountPartCodes + "].")
            );
        }

    }

    private Collection<BalanceAccountPart> mapCommandToBalanceAccountParts(CreateBalanceAccountPartCommand command) {
        return command.getBalanceAccountParts()
                .stream()
                .map(balanceAccountPartCommand ->
                        BalanceAccountPart.withoutId(
                                balanceAccountPartCommand.getCode(),
                                command.getBalanceAccountPartLevelId(),
                                Multilingual.fromMap(balanceAccountPartCommand.getDescriptions())
                        )
                )
                .collect(Collectors.toUnmodifiableList());

    }
}
