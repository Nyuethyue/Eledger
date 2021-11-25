package bhutan.eledger.adapter.out.persistence.eledger.config.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface TransactionTypeEntityRepository extends JpaRepository<TransactionTypeEntity, Long> {
    boolean existsByName(String name);

    Optional<TransactionTypeEntity> findByName(String name);
}
