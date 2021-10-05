package bhutan.eledger.adapter.persistence.config.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

interface TransactionTypeEntityRepository extends JpaRepository<TransactionTypeEntity, Long> {
    boolean existsByName(String name);
}
