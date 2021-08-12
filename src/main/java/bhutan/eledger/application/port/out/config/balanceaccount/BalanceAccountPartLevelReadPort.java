package bhutan.eledger.application.port.out.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartLevel;

import java.util.Collection;
import java.util.Optional;

public interface BalanceAccountPartLevelReadPort {

    Optional<BalanceAccountPartLevel> readById(Long id);

    Collection<BalanceAccountPartLevel> readAll();
}
