package bhutan.eledger.application.service.eledger.config.transaction;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.eledger.config.transaction.CreateTransactionTypeUseCase;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeRepositoryPort;
import bhutan.eledger.domain.eledger.config.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateTransactionTypeService implements CreateTransactionTypeUseCase {
    private final TransactionTypeRepositoryPort transactionTypeRepositoryPort;

    @Override
    public Long create(CreateTransactionTypeCommand command) {
        log.trace("Creating transaction type with command: {}", command);

        var transactionType = commandToTransactionType(command);

        validate(transactionType);

        log.trace("Persisting transaction type: {}", transactionType);

        Long id = transactionTypeRepositoryPort.create(transactionType);

        log.debug("Transaction type with id: {} successfully created.", id);

        return id;
    }

    private void validate(TransactionType transactionType) {
        if (transactionTypeRepositoryPort.existsByName(transactionType.getName())) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("transactionType", "Transaction type with name: [" + transactionType.getName() + "] already exists.")
            );
        }
    }

    private TransactionType commandToTransactionType(CreateTransactionTypeCommand command) {
        return TransactionType.withoutId(
                command.getName(),
                Multilingual.fromMap(command.getDescriptions())
        );
    }
}
