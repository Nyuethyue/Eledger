package bhutan.eledger.adapter.persistence.eledger.config.glaccount;

import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Collection;
import java.util.List;

interface GLAccountPartEntityRepository extends CustomQuerydslJpaRepository<GLAccountPartEntity, Long>, RevisionRepository<GLAccountPartEntity, Long, Long> {


    @Query("SELECT bap " +
            "FROM GLAccountPartEntity bap " +
            "JOIN GLAccountPartTypeEntity bapt ON bap.glAccountPartTypeId = bapt.id " +
            "WHERE bap.id IN (:ids) " +
            "ORDER BY bapt.level ASC")
    List<GLAccountPartEntity> queryAllByIdInSortedByLevel(Collection<Long> ids);

    Collection<GLAccountPartEntity> readAllByParentId(Long parentId);

    Collection<GLAccountPartEntity> readAllByGlAccountPartTypeId(Integer partTypeId);

    boolean existsByParentIdAndCodeIn(Long id, Collection<String> codes);
}
