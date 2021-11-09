package bhutan.eledger.application.service.eledger.transaction;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.eledger.config.transaction.ReadTransactionTypeUseCase;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeTransactionTypeAttributeRepositoryPort;
import bhutan.eledger.domain.eledger.config.transaction.TransactionType;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeAttribute;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeWithAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadTransactionTypeService implements ReadTransactionTypeUseCase {
    private final TransactionTypeRepositoryPort transactionTypeRepositoryPort;
    private final TransactionTypeTransactionTypeAttributeRepositoryPort transactionTypeTransactionTypeAttributeRepositoryPort;

    @Override
    public TransactionType readById(Long id) {
        log.trace("Reading transaction type by id: {}", id);

        return transactionTypeRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("Transaction type by id: [" + id + "] not found.")
                );
    }

    @Override
    public TransactionTypeWithAttributes readWithAttributesById(Long id) {
        log.trace("Reading transaction type with attributes by transaction type id: {}", id);

        TransactionType transactionType = readById(id);

        Collection<TransactionTypeAttribute> attributes = transactionTypeTransactionTypeAttributeRepositoryPort.readAllTransactionTypeAttributesByTransactionTypeId(id);

        return transactionType.withAttributes(attributes);
    }

    @Override
    public Collection<TransactionType> readAll() {
        log.trace("Reading all transaction types.");

        return transactionTypeRepositoryPort.readAll();
    }
}
