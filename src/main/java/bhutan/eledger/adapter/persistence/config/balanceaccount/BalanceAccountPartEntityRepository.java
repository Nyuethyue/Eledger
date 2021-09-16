package bhutan.eledger.adapter.persistence.config.balanceaccount;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Collection;
import java.util.List;

interface BalanceAccountPartEntityRepository extends CustomQuerydslJpaRepository<BalanceAccountPartEntity, Long>, RevisionRepository<BalanceAccountPartEntity, Long, Long> {


    @Query("SELECT bap " +
            "FROM BalanceAccountPartEntity bap " +
            "JOIN BalanceAccountPartTypeEntity bapt ON bap.balanceAccountPartTypeId = bapt.id " +
            "WHERE bap.id IN (:ids) " +
            "ORDER BY bapt.level ASC")
    List<BalanceAccountPartEntity> queryAllByIdInSortedByLevel(Collection<Long> ids);

    Collection<BalanceAccountPartEntity> readAllByParentId(Long parentId);

    boolean existsByParentIdAndCodeIn(Long id, Collection<String> codes);
}
