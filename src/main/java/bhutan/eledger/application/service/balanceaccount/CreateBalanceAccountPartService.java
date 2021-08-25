package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateBalanceAccountPartService implements CreateBalanceAccountPartUseCase {
    private final BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;
    private final BalanceAccountPartTypeValidator balanceAccountPartTypeValidator;
    private final BalanceAccountPartValidator balanceAccountPartValidator;

    @Override
    public Collection<BalanceAccountPart> create(CreateBalanceAccountPartCommand command) {

        log.trace("Creating balance account part with command: {}", command);

        validate(command);

        var balanceAccountParts = mapCommandToBalanceAccountParts(command);

        log.trace("Persisting balance account parts: {}", balanceAccountParts);

        var persistedBalanceAccountParts = balanceAccountPartRepositoryPort.create(balanceAccountParts);

        log.debug("Balance account part with codes: {} successfully created.", () -> persistedBalanceAccountParts.stream().map(BalanceAccountPart::getCode).collect(Collectors.toUnmodifiableList()));


        return persistedBalanceAccountParts;
    }

    private void validate(CreateBalanceAccountPartCommand command) {
        balanceAccountPartTypeValidator.checkExistenceById(command.getBalanceAccountPartTypeId());
        balanceAccountPartValidator.checkPartsExistence(
                command.getParentId(),
                command.getBalanceAccountParts()
                        .stream()
                        .map(BalanceAccountPartCommand::getCode)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    private Collection<BalanceAccountPart> mapCommandToBalanceAccountParts(CreateBalanceAccountPartCommand command) {

        LocalDateTime creationDateTime = LocalDateTime.now();

        return command.getBalanceAccountParts()
                .stream()
                .map(balanceAccountPartCommand ->
                        BalanceAccountPart.withoutId(
                                balanceAccountPartCommand.getCode(),
                                command.getParentId(),
                                BalanceAccountPartStatus.ACTIVE,
                                creationDateTime,
                                creationDateTime,
                                creationDateTime.toLocalDate().atStartOfDay(),
                                null,
                                Multilingual.fromMap(balanceAccountPartCommand.getDescriptions()),
                                command.getBalanceAccountPartTypeId()
                        )
                )
                .collect(Collectors.toUnmodifiableList());
    }
}
