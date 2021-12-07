package bhutan.eledger.adapter.out.eledger.persistence.config.glaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface GLAccountPartTypeEntityRepository extends JpaRepository<GLAccountPartTypeEntity, Integer> {
    boolean existsByLevel(Integer level);

    @Query("SELECT bapt.id " +
            "FROM GLAccountPartTypeEntity bapt " +
            "WHERE bapt.level = (" +
            "SELECT fbapt.level + 1 " +
            "FROM GLAccountPartTypeEntity fbapt " +
            "WHERE fbapt.id = :fromPartTypeId " +
            ")")
    Optional<Integer> getIdOfNextPartType(Integer fromPartTypeId);

    Optional<GLAccountPartTypeEntity> findByLevel(Integer level);
}
