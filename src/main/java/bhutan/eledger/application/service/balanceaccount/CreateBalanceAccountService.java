package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartStatus;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateBalanceAccountService implements CreateBalanceAccountUseCase {
    private final BalanceAccountRepositoryPort balanceAccountRepositoryPort;
    private final BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;

    @Override
    public Long create(CreateBalanceAccountCommand command) {
        log.trace("Creating balance account with command: {}", command);

        BalanceAccountPart lastPart = mapCommandToBalanceAccount(command.getBalanceAccountLastPart());

        log.trace("Persisting balance account last part: {}", lastPart);

        Long lastPartId = balanceAccountPartRepositoryPort.create(lastPart);

        log.debug("Balance account last part with id: {} successfully created.", lastPartId);

        String bankAccountCodeWithoutLastPart = command.getBalanceAccountParts()
                .stream()
                .sorted(Comparator.comparing(BalanceAccountPartCommand::getBalanceAccountPartTypeId))
                .map(BalanceAccountPartCommand::getCode)
                .collect(Collectors.joining());

        BalanceAccount balanceAccount = BalanceAccount.withoutId(
                bankAccountCodeWithoutLastPart + lastPart.getCode(),
                lastPartId,
                BalanceAccountStatus.ACTIVE,
                LocalDate.now(),
                null,
                Multilingual.fromMap(command.getDescriptions())
        );

        log.trace("Persisting balance account: {}", balanceAccount);

        Long id = balanceAccountRepositoryPort.create(balanceAccount);

        log.debug("Balance account with id: {} successfully created.", id);

        return id;
    }

    private BalanceAccountPart mapCommandToBalanceAccount(BalanceAccountLastPartCommand command) {

        return BalanceAccountPart.withoutId(
                command.getCode(),
                command.getParentId(),
                command.getBalanceAccountPartTypeId(),
                BalanceAccountPartStatus.ACTIVE,
                LocalDate.now(),
                null,
                Multilingual.fromMap(command.getDescriptions())
        );
    }
}
