package bhutan.eledger.application.port.out.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;

import java.util.Collection;
import java.util.Optional;

public interface BalanceAccountPartTypeRepositoryPort {

    Optional<BalanceAccountPartType> readById(Integer id);

    Collection<BalanceAccountPartType> readAll();

    Integer create(BalanceAccountPartType balanceAccountPartType);

    boolean existById(Integer id);

    boolean existByLevel(Integer level);
}
