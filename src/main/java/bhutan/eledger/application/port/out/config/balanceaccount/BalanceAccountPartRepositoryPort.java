package bhutan.eledger.application.port.out.config.balanceaccount;

import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BalanceAccountPartRepositoryPort {

    Optional<BalanceAccountPart> readById(Long id);

    List<BalanceAccountPart> readAllByIdInSortedByLevel(Collection<Long> ids);

    Collection<BalanceAccountPart> readAllByParentId(Long parentId);

    Collection<BalanceAccountPart> readAll();

    Long create(BalanceAccountPart balanceAccountPart);

    Collection<BalanceAccountPart> create(Collection<BalanceAccountPart> balanceAccountParts);

    boolean existByParentIdAndCodeInList(Long parentId, Collection<String> codes);

    void deleteAll();
}
