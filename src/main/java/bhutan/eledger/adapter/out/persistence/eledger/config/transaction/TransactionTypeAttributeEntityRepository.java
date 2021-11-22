package bhutan.eledger.adapter.out.persistence.eledger.config.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

interface TransactionTypeAttributeEntityRepository extends JpaRepository<TransactionTypeAttributeEntity, Long> {
    boolean existsByName(String name);

    boolean existsByNameIn(Collection<String> name);
}
