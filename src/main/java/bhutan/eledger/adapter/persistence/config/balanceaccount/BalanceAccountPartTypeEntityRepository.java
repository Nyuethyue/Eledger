package bhutan.eledger.adapter.persistence.config.balanceaccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface BalanceAccountPartTypeEntityRepository extends JpaRepository<BalanceAccountPartTypeEntity, Integer> {
    boolean existsByLevel(Integer level);

    @Query("SELECT bapt.id " +
            "FROM BalanceAccountPartTypeEntity bapt " +
            "WHERE bapt.level = (" +
            "SELECT fbapt.level + 1 " +
            "FROM BalanceAccountPartTypeEntity fbapt " +
            "WHERE fbapt.id = :fromPartTypeId " +
            ")")
    Optional<Integer> getIdOfNextPartType(Integer fromPartTypeId);

    Optional<BalanceAccountPartTypeEntity> findByLevel(Integer level);
}
