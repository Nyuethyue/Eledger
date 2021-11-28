package bhutan.eledger.adapter.out.persistence.eledger.config.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface TransactionTypeEntityRepository extends JpaRepository<TransactionTypeEntity, Long> {
    boolean existsByCode(String code);

    Optional<TransactionTypeEntity> findByCode(String code);
}
