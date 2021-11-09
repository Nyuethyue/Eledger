package bhutan.eledger.application.service.eledger.transaction;

import bhutan.eledger.application.port.in.eledger.config.transaction.TransactionTypeTransactionTypeAttributesRelationUseCase;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeTransactionTypeAttributeRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class TransactionTypeTransactionTypeAttributesRelationService implements TransactionTypeTransactionTypeAttributesRelationUseCase {
    private final TransactionTypeTransactionTypeAttributeRepositoryPort transactionTypeTransactionTypeAttributeRepositoryPort;

    @Override
    public void addTransactionTypeAttributesToTransactionTypeByIds(Long transactionTypeId, TransactionAttributeCommand command) {
        log.trace("Adding transaction type attributes to transaction types. Command: {}", command);


        transactionTypeTransactionTypeAttributeRepositoryPort.addTransactionTypeAttributesToTransactionType(
                transactionTypeId,
                command.getTransactionTypeAttributeIds()
        );
    }

    @Override
    public void removeTransactionTypeAttributesFromTransactionTypeByIds(Long transactionTypeId, TransactionAttributeCommand command) {
        transactionTypeTransactionTypeAttributeRepositoryPort.removeAllFromTransactionTypeByTransactionTypeAttributeIds(transactionTypeId, command.getTransactionTypeAttributeIds());
    }
}
