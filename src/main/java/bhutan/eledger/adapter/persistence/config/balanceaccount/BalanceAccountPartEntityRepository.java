package bhutan.eledger.adapter.persistence.config.balanceaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

interface BalanceAccountPartEntityRepository extends JpaRepository<BalanceAccountPartEntity, Long> {


    @Query("SELECT bap " +
            "FROM BalanceAccountPartEntity bap " +
            "JOIN BalanceAccountPartTypeEntity bapt ON bap.balanceAccountPartTypeId = bapt.id " +
            "WHERE bap.id IN (:ids) " +
            "ORDER BY bapt.level ASC")
    List<BalanceAccountPartEntity> queryAllByIdInSortedByLevel(Collection<Long> ids);

    boolean existsByParentIdAndCodeIn(Long id, Collection<String> codes);
}
