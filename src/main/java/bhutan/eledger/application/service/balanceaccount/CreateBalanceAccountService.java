package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.Violation;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartStatus;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateBalanceAccountService implements CreateBalanceAccountUseCase {
    private final BalanceAccountRepositoryPort balanceAccountRepositoryPort;
    private final BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;
    private final BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort;

    @Override
    public Long create(CreateBalanceAccountCommand command) {
        log.trace("Creating balance account with command: {}", command);

        List<BalanceAccountPart> balanceAccountParts =
                balanceAccountPartRepositoryPort.readAllByIdInSortedByLevel(command.getBalanceAccountPartIds());
        //todo validate ids count and result count matching

        String bankAccountCodeWithoutLastPart = balanceAccountParts
                .stream()
                .map(BalanceAccountPart::getCode)
                .collect(Collectors.joining());

        var parentOfLastPart = balanceAccountParts.get(balanceAccountParts.size() - 1);

        BalanceAccountPart lastPart = mapCommandToBalanceAccount(
                command.getBalanceAccountLastPart(),
                parentOfLastPart.getId(),
                balanceAccountPartTypeRepositoryPort.getIdOfNextPartType(parentOfLastPart.getBalanceAccountPartLevelId())
                        .orElseThrow(() -> new ViolationException(
                                        new ValidationError(
                                                Set.of(
                                                        new Violation("partType", "Part type for last part not found.")
                                                )
                                        )
                                )
                        )
        );

        log.trace("Persisting balance account last part: {}", lastPart);

        Long lastPartId = balanceAccountPartRepositoryPort.create(lastPart);

        log.debug("Balance account last part with id: {} successfully created.", lastPartId);

        LocalDateTime creationDateTime = LocalDateTime.now();

        BalanceAccount balanceAccount = BalanceAccount.withoutId(
                bankAccountCodeWithoutLastPart + lastPart.getCode(),
                BalanceAccountStatus.ACTIVE,
                creationDateTime,
                creationDateTime.toLocalDate().atStartOfDay(),
                null,
                Multilingual.fromMap(command.getDescriptions()),
                lastPartId
        );

        log.trace("Persisting balance account: {}", balanceAccount);

        Long id = balanceAccountRepositoryPort.create(balanceAccount);

        log.debug("Balance account with id: {} successfully created.", id);

        return id;
    }

    private BalanceAccountPart mapCommandToBalanceAccount(BalanceAccountLastPartCommand command, Long parentId, Integer balanceAccountPartTypeId) {

        LocalDateTime creationDateTime = LocalDateTime.now();

        return BalanceAccountPart.withoutId(
                command.getCode(),
                parentId,
                BalanceAccountPartStatus.ACTIVE,
                creationDateTime,
                creationDateTime.toLocalDate().atStartOfDay(),
                null,
                Multilingual.fromMap(command.getDescriptions()),
                balanceAccountPartTypeId
        );
    }
}
