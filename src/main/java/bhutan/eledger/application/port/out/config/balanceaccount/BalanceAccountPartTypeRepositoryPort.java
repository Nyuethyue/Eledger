package bhutan.eledger.application.port.out.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;

import java.util.Collection;
import java.util.Optional;

public interface BalanceAccountPartTypeRepositoryPort {

    Optional<BalanceAccountPartType> readById(Integer id);

    Optional<BalanceAccountPartType> readByLevel(Integer level);

    Collection<BalanceAccountPartType> readAll();

    Integer create(BalanceAccountPartType balanceAccountPartType);

    Optional<Integer> getIdOfNextPartType(Integer fromPartTypeId);

    boolean existById(Integer id);

    boolean existByLevel(Integer level);

    void deleteById(Integer id);

    void deleteAll();
}
