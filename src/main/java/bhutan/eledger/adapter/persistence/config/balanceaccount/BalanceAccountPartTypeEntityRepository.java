package bhutan.eledger.adapter.persistence.config.balanceaccount;

import org.springframework.data.jpa.repository.JpaRepository;

interface BalanceAccountPartTypeEntityRepository extends JpaRepository<BalanceAccountPartTypeEntity, Integer> {
    boolean existsByLevel(Integer level);
}
