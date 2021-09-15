package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.config.balanceaccount.UpdateBalanceAccountUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountStatus;
import bhutan.eledger.domain.config.balanceaccount.ValidityPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpdateBalanceAccountService implements UpdateBalanceAccountUseCase {
    private final BalanceAccountRepositoryPort balanceAccountRepositoryPort;

    @Override
    public void updateBalanceAccount(Long id, UpdateBalanceAccountCommand command) {
        log.trace("Updating balance account with id: {} by command: {}", id, command);

        BalanceAccount balanceAccount = balanceAccountRepositoryPort.readById(id).orElseThrow(() ->
                new RecordNotFoundException("BalanceAccount by id: [" + id + "] not found.")
        );

        log.trace("Balance account to be updated: {}", balanceAccount);

        //todo apply validation logic

        ValidityPeriod validityPeriod = resolveValidityPeriod(balanceAccount, command);

        BalanceAccount updatedBalanceAccount = BalanceAccount.withId(
                balanceAccount.getId(),
                balanceAccount.getCode(),
                command.getBalanceAccountStatus(),
                balanceAccount.getCreationDateTime(),
                LocalDateTime.now(),
                validityPeriod,
                balanceAccount.getDescription().merge(command.getDescriptions()),
                balanceAccount.getBalanceAccountLastPartId()
        );

        log.trace("Persisting updated balance account: {}", updatedBalanceAccount);

        balanceAccountRepositoryPort.update(updatedBalanceAccount);
    }

    private ValidityPeriod resolveValidityPeriod(BalanceAccount balanceAccount, UpdateBalanceAccountCommand command) {

        ValidityPeriod result;

        if (balanceAccount.getStatus() == command.getBalanceAccountStatus()) {
            result = balanceAccount.getValidityPeriod();
        } else if (command.getBalanceAccountStatus() == BalanceAccountStatus.INACTIVE) {
            result = ValidityPeriod.of(balanceAccount.getValidityPeriod().getStart(), command.getActualDate());
        } else {
            result = ValidityPeriod.withOnlyStartOfValidity(command.getActualDate());
        }

        return result;
    }
}
