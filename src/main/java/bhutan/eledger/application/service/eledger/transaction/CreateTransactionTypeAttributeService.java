package bhutan.eledger.application.service.eledger.transaction;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.eledger.config.transaction.CreateTransactionTypeAttributeUseCase;
import bhutan.eledger.application.port.out.eledger.config.datatype.DataTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeAttributeRepositoryPort;
import bhutan.eledger.domain.eledger.config.datatype.DataType;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateTransactionTypeAttributeService implements CreateTransactionTypeAttributeUseCase {
    private final TransactionTypeAttributeRepositoryPort transactionTypeAttributeRepositoryPort;
    private final DataTypeRepositoryPort dataTypeRepositoryPort;

    @Override
    public Collection<TransactionTypeAttribute> create(@Valid CreateTransactionTypeAttributesCommand command) {
        log.trace("Creating transaction type attribute with command: {}", command);

        var transactionTypeAttributes = commandToTransactionTypeAttributes(command);

        checkAttributeExistenceByName(transactionTypeAttributes);

        log.trace("Persisting transaction type attributes: {}", transactionTypeAttributes);

        var persistedTransactionTypeAttributes = transactionTypeAttributeRepositoryPort.create(transactionTypeAttributes);

        log.trace("Transaction type attributes: {} successfully created.", persistedTransactionTypeAttributes);

        return persistedTransactionTypeAttributes;
    }

    private void checkAttributeExistenceByName(Collection<TransactionTypeAttribute> transactionTypeAttributes) {
        var attrNames = transactionTypeAttributes
                .stream()
                .map(TransactionTypeAttribute::getName)
                .collect(Collectors.toUnmodifiableList());

        if (transactionTypeAttributeRepositoryPort.existsByAnyName(attrNames)) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("transactionTypeAttributes", "Transaction type attribute with one of these names: [" + attrNames + "] already exists.")
            );
        }
    }

    private Collection<TransactionTypeAttribute> commandToTransactionTypeAttributes(CreateTransactionTypeAttributesCommand command) {
        return command.getTransactionTypeAttributes()
                .stream()
                .map(this::commandToTransactionTypeAttribute)
                .collect(Collectors.toUnmodifiableList());
    }

    private TransactionTypeAttribute commandToTransactionTypeAttribute(TransactionTypeAttributeCommand command) {
        DataType dataType = dataTypeRepositoryPort.readById(command.getDataTypeId())
                .orElseThrow(() -> new ViolationException(
                                new ValidationError()
                                        .addViolation("dataTypeId", "Data type with id: [" + command.getDataTypeId() + "] doesn't exists.")
                        )
                );

        return TransactionTypeAttribute.withoutId(
                command.getName(),
                dataType,
                Multilingual.fromMap(command.getDescriptions())
        );
    }
}
