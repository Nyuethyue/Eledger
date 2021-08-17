package bhutan.eledger.application.port.out.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;

import java.util.Collection;
import java.util.Optional;

public interface BalanceAccountRepositoryPort {

    Optional<BalanceAccount> readById(Long id);

    Collection<BalanceAccount> readAll();

    Long create(BalanceAccount BalanceAccount);

    Collection<BalanceAccount> create(Collection<BalanceAccount> BalanceAccounts);

}
