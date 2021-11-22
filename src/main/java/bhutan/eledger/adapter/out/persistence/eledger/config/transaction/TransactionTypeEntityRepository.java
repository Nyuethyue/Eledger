package bhutan.eledger.adapter.out.persistence.eledger.config.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

interface TransactionTypeEntityRepository extends JpaRepository<TransactionTypeEntity, Long> {
    boolean existsByName(String name);
}
