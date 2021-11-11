package bhutan.eledger.adapter.persistence.eledger.transaction;

import bhutan.eledger.domain.eledger.transaction.Transaction;
import bhutan.eledger.domain.eledger.transaction.TransactionAttribute;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class TransactionMapper {

    TransactionEntity mapToEntity(Transaction transaction) {
        TransactionEntity transactionEntity = new TransactionEntity(
                transaction.getId(),
                transaction.getDrn(),
                transaction.getTaxpayerId(),
                transaction.getGlAccountCode(),
                transaction.getSettlementDate(),
                transaction.getAmount(),
                transaction.getCreationDateTime(),
                transaction.getTransactionTypeId()
        );

        transactionEntity.setTransactionAttributes(
                transaction.getTransactionAttributes()
                        .stream()
                        .map(ta ->
                                new TransactionAttributeEntity(
                                        ta.getId(),
                                        ta.getTransactionTypeAttributeId(),
                                        ta.getValue(),
                                        transactionEntity
                                )
                        )
                        .collect(Collectors.toSet())
        );

        return transactionEntity;
    }

    Transaction mapToDomain(TransactionEntity transactionEntity) {
        return Transaction.withId(
                transactionEntity.getId(),
                transactionEntity.getDrn(),
                transactionEntity.getTaxpayerId(),
                transactionEntity.getGlAccountCode(),
                transactionEntity.getSettlementDate(),
                transactionEntity.getAmount(),
                transactionEntity.getCreationDateTime(),
                transactionEntity.getTransactionTypeId(),
                transactionEntity.getTransactionAttributes()
                        .stream()
                        .map(ta ->
                                TransactionAttribute.withId(
                                        ta.getId(),
                                        ta.getTransactionTypeAttributeId(),
                                        ta.getValue()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
