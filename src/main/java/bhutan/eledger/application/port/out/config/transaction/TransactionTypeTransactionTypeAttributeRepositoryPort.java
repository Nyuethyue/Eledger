package bhutan.eledger.application.port.out.config.transaction;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.config.transaction.TransactionTypeAttribute;

import java.util.Collection;

public interface TransactionTypeTransactionTypeAttributeRepositoryPort {

    /**
     * @param transactionTypeId           transaction type id
     * @param transactionTypeAttributeIds transaction type attribute id
     *
     * @throws RecordNotFoundException when the transaction type by id not found or any transaction type attribute not by provided transactionTypeAttributeIds
     */
    void addTransactionTypeAttributesToTransactionType(Long transactionTypeId, Collection<Long> transactionTypeAttributeIds);

    Collection<TransactionTypeAttribute> readAllTransactionTypeAttributesByTransactionTypeId(Long transactionTypeId);

    void removeAllFromTransactionTypeByTransactionTypeAttributeIds(Long transactionTypeId, Collection<Long> transactionTypeAttrIds);

    void deleteAll();
}
