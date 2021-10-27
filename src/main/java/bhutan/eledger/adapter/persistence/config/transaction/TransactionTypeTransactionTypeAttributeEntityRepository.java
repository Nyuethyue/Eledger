package bhutan.eledger.adapter.persistence.config.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

interface TransactionTypeTransactionTypeAttributeEntityRepository extends JpaRepository<TransactionTypeTransactionTypeAttributeEntity, TransactionTypeTransactionTypeAttrId> {
    Collection<TransactionTypeTransactionTypeAttributeEntity> findAllByTransactionTypeId(Long id);
}
