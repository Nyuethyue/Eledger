package bhutan.eledger.adapter.persistence.config.balanceaccount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

interface BalanceAccountPartEntityRepository extends JpaRepository<BalanceAccountPartEntity, Long> {

    boolean existsByParentIdAndCodeIn(Long id, Collection<String> codes);
}
