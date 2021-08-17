package bhutan.eledger.application.port.out.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;

import java.util.Collection;
import java.util.Optional;

public interface BalanceAccountPartTypeRepositoryPort {

    Optional<BalanceAccountPartType> readById(Long id);

    Collection<BalanceAccountPartType> readAll();

    Long create(BalanceAccountPartType balanceAccountPartType);

    boolean existById(Integer id);

    boolean existByLevel(Integer level);
}
