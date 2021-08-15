package bhutan.eledger.application.port.out.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartLevel;

import java.util.Collection;
import java.util.Optional;

public interface BalanceAccountPartLevelRepositoryPort {

    Optional<BalanceAccountPartLevel> readById(Long id);

    Collection<BalanceAccountPartLevel> readAll();

    Long create(BalanceAccountPartLevel balanceAccountPartLevel);

    boolean existById(Integer id);
}
