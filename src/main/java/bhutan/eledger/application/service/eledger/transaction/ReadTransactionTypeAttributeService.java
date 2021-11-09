package bhutan.eledger.application.service.eledger.transaction;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.eledger.config.transaction.ReadTransactionTypeAttributeUseCase;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeAttributeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeTransactionTypeAttributeRepositoryPort;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadTransactionTypeAttributeService implements ReadTransactionTypeAttributeUseCase {
    private final TransactionTypeAttributeRepositoryPort transactionTypeAttributeRepositoryPort;
    private final TransactionTypeTransactionTypeAttributeRepositoryPort transactionTypeTransactionTypeAttributeRepositoryPort;

    @Override
    public TransactionTypeAttribute readById(Long id) {
        log.trace("Reading transaction type attribute by id: {}", id);

        return transactionTypeAttributeRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("Transaction type by id: [" + id + "] not found.")
                );
    }

    @Override
    public Collection<TransactionTypeAttribute> readAllByTransactionTypeId(Long transactionTypeId) {
        log.trace("Reading transaction type attributes by transaction type id: {}", transactionTypeId);

        return transactionTypeTransactionTypeAttributeRepositoryPort.readAllTransactionTypeAttributesByTransactionTypeId(transactionTypeId);
    }

    @Override
    public Collection<TransactionTypeAttribute> readAll() {
        log.trace("Reading all transaction type attributes.");

        return transactionTypeAttributeRepositoryPort.readAll();
    }
}
