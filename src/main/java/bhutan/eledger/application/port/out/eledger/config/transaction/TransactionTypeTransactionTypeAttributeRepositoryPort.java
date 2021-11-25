package bhutan.eledger.application.port.out.eledger.config.transaction;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeAttribute;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeWithAttributes;

import java.util.Collection;
import java.util.Optional;

public interface TransactionTypeTransactionTypeAttributeRepositoryPort {

    /**
     * @param transactionTypeId           transaction type id
     * @param transactionTypeAttributeIds transaction type attribute id
     *
     * @throws RecordNotFoundException when the transaction type by id not found or any transaction type attribute not by provided transactionTypeAttributeIds
     */
    void addTransactionTypeAttributesToTransactionType(Long transactionTypeId, Collection<Long> transactionTypeAttributeIds);

    Collection<TransactionTypeAttribute> readAllTransactionTypeAttributesByTransactionTypeId(Long transactionTypeId);

    Optional<TransactionTypeWithAttributes> readTransactionWithAttributesById(Long id);

    Optional<TransactionTypeWithAttributes> readTransactionWithAttributesByName(String name);

    TransactionTypeWithAttributes requiredReadTransactionWithAttributesById(Long id);

    TransactionTypeWithAttributes requiredReadTransactionWithAttributesByName(String name);

    void removeAllFromTransactionTypeByTransactionTypeAttributeIds(Long transactionTypeId, Collection<Long> transactionTypeAttrIds);

    void deleteAll();
}
