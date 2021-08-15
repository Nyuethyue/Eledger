package bhutan.eledger.application.port.out.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;

import java.util.Collection;
import java.util.Optional;

public interface BalanceAccountPartRepositoryPort {

    Optional<BalanceAccountPart> readById(Long id);

    Collection<BalanceAccountPart> readAll();

    Long create(BalanceAccountPart balanceAccountPart);

    Collection<BalanceAccountPart> create(Collection<BalanceAccountPart> balanceAccountParts);

    boolean existByParentIdAndCodeInList(Integer parentId, Collection<String> codes);
}
