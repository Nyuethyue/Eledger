package bhutan.eledger.adapter.out.eledger.persistence.config.transaction;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.out.eledger.config.datatype.DataTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeTransactionTypeAttributeRepositoryPort;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeAttribute;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeWithAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class TransactionTypeTransactionTypeAttributeAdapter implements TransactionTypeTransactionTypeAttributeRepositoryPort {
    private final TransactionTypeEntityRepository transactionTypeEntityRepository;
    private final TransactionTypeAttributeEntityRepository transactionTypeAttributeEntityRepository;
    private final TransactionTypeTransactionTypeAttributeEntityRepository transactionTypeTransactionTypeAttributeEntityRepository;
    private final TransactionTypeAttributeMapper transactionTypeAttributeMapper;
    private final DataTypeRepositoryPort dataTypeRepositoryPort;
    private final TransactionTypeRepositoryPort transactionTypeRepositoryPort;


    public void addTransactionTypeAttributesToTransactionType(Long transactionTypeId, Collection<Long> transactionTypeAttributeIds) {
        TransactionTypeEntity transactionTypeEntity = transactionTypeEntityRepository.findById(transactionTypeId)
                .orElseThrow(() ->
                        new RecordNotFoundException("TransactionType by id: [" + transactionTypeId + "] not found.")
                );
        Collection<TransactionTypeAttributeEntity> transactionTypeAttributeEntities = transactionTypeAttributeEntityRepository.findAllById(transactionTypeAttributeIds);

        validateTransactionTypeAttributesExistence(transactionTypeAttributeIds, transactionTypeAttributeEntities);

        var transactionTypeTransactionTypeAttributeEntities = transactionTypeAttributeEntities
                .stream()
                .map(transactionTypeAttributeEntity ->
                        new TransactionTypeTransactionTypeAttributeEntity(transactionTypeEntity, transactionTypeAttributeEntity)
                )
                .collect(Collectors.toList());

        transactionTypeTransactionTypeAttributeEntityRepository.saveAll(transactionTypeTransactionTypeAttributeEntities);
    }

    public Collection<TransactionTypeAttribute> readAllTransactionTypeAttributesByTransactionTypeId(Long transactionTypeId) {
        return transactionTypeTransactionTypeAttributeEntityRepository.findAllByTransactionTypeId(transactionTypeId)
                .stream()
                .map(TransactionTypeTransactionTypeAttributeEntity::getTransactionTypeAttribute)
                .map(transactionTypeAttributeEntity ->
                        transactionTypeAttributeMapper.mapToDomain(
                                transactionTypeAttributeEntity,
                                dataTypeRepositoryPort.requiredReadById(transactionTypeAttributeEntity.getDataTypeId())
                        )
                )
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TransactionTypeWithAttributes> readTransactionWithAttributesById(Long id) {
        return transactionTypeRepositoryPort.readById(id)
                .map(transactionType -> transactionType.withAttributes(
                                readAllTransactionTypeAttributesByTransactionTypeId(transactionType.getId())
                        )
                );
    }

    @Override
    public Optional<TransactionTypeWithAttributes> readTransactionWithAttributesByCode(String code) {
        return transactionTypeRepositoryPort.readByCode(code)
                .map(transactionType -> transactionType.withAttributes(
                                readAllTransactionTypeAttributesByTransactionTypeId(transactionType.getId())
                        )
                );
    }

    @Override
    public TransactionTypeWithAttributes requiredReadTransactionWithAttributesById(Long id) {
        return readTransactionWithAttributesById(id)
                .orElseThrow(() -> new RecordNotFoundException("TransactionType by id: [" + id + "] not found."));
    }

    @Override
    public TransactionTypeWithAttributes requiredReadTransactionWithAttributesByCode(String code) {
        return readTransactionWithAttributesByCode(code)
                .orElseThrow(() -> new RecordNotFoundException("TransactionType by code: [" + code + "] not found."));
    }

    @Override
    public void removeAllFromTransactionTypeByTransactionTypeAttributeIds(Long transactionTypeId, Collection<Long> transactionTypeAttrIds) {
        transactionTypeTransactionTypeAttributeEntityRepository.deleteAllById(
                transactionTypeAttrIds
                        .stream()
                        .map(transactionTypeAttributeId -> new TransactionTypeTransactionTypeAttrId(transactionTypeId, transactionTypeAttributeId))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void deleteAll() {
        transactionTypeTransactionTypeAttributeEntityRepository.deleteAll();
    }

    private void validateTransactionTypeAttributesExistence(Collection<Long> transactionTypeAttributeIds, Collection<TransactionTypeAttributeEntity> transactionTypeAttributeEntities) {
        if (transactionTypeAttributeIds.size() != transactionTypeAttributeEntities.size()) {
            if (transactionTypeAttributeIds.size() > transactionTypeAttributeEntities.size()) {
                Collection<Long> foundedTransactionTypeAttributeIds = transactionTypeAttributeEntities
                        .stream().map(TransactionTypeAttributeEntity::getId)
                        .collect(Collectors.toUnmodifiableSet());

                Collection<Long> notFoundedIds = transactionTypeAttributeIds
                        .stream()
                        .filter(transactionTypeAttributeId -> !foundedTransactionTypeAttributeIds.contains(transactionTypeAttributeId))
                        .collect(Collectors.toList());

                throw new RecordNotFoundException("Transaction type attributes by ids: " + notFoundedIds + " not found");
            } else {
                throw new IllegalStateException("Was provided " + transactionTypeAttributeIds.size() + " ids but founded " + transactionTypeAttributeEntities.size() + " records.");
            }
        }
    }
}
