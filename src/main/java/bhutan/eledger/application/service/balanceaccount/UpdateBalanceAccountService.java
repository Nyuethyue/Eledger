package bhutan.eledger.application.service.balanceaccount;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.config.balanceaccount.UpdateBalanceAccountUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
class UpdateBalanceAccountService implements UpdateBalanceAccountUseCase {
    private final BalanceAccountRepositoryPort balanceAccountRepositoryPort;

    @Override
    public void updateBalanceAccount(Long id, UpdateBalanceAccountCommand command) {
        BalanceAccount balanceAccount = balanceAccountRepositoryPort.readById(id).orElseThrow(() ->
                new RecordNotFoundException("BalanceAccount by id: [" + id + "] not found.")
        );

        BalanceAccount updatedBalanceAccount = BalanceAccount.withId(
                balanceAccount.getId(),
                balanceAccount.getCode(),
                command.getBalanceAccountStatus(),
                balanceAccount.getCreationDateTime(),
                LocalDateTime.now(),
                balanceAccount.getStartDate(),
                balanceAccount.getEndDate(),
                balanceAccount.getDescription().merge(command.getDescriptions()),
                balanceAccount.getBalanceAccountLastPartId()
        );

        balanceAccountRepositoryPort.update(updatedBalanceAccount);

    }
}
