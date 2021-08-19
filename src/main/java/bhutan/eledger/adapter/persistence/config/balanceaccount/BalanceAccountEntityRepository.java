package bhutan.eledger.adapter.persistence.config.balanceaccount;

import org.springframework.data.jpa.repository.JpaRepository;

interface BalanceAccountEntityRepository extends JpaRepository<BalanceAccountEntity, Long> {

}
