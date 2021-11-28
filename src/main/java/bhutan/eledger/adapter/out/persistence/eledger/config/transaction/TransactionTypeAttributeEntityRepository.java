package bhutan.eledger.adapter.out.persistence.eledger.config.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

interface TransactionTypeAttributeEntityRepository extends JpaRepository<TransactionTypeAttributeEntity, Long> {
    boolean existsByCode(String code);

    Optional<TransactionTypeAttributeEntity> findByCode(String code);

    boolean existsByCodeIn(Collection<String> code);
}
