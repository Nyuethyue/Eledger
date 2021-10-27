package bhutan.eledger.adapter.persistence.config.transaction;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.out.config.datatype.DataTypeRepositoryPort;
import bhutan.eledger.application.port.out.config.transaction.TransactionTypeTransactionTypeAttributeRepositoryPort;
import bhutan.eledger.domain.config.transaction.TransactionTypeAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class TransactionTypeTransactionTypeAttributeAdapter implements TransactionTypeTransactionTypeAttributeRepositoryPort {
    private final TransactionTypeEntityRepository transactionTypeEntityRepository;
    private final TransactionTypeAttributeEntityRepository transactionTypeAttributeEntityRepository;
    private final TransactionTypeTransactionTypeAttributeEntityRepository transactionTypeTransactionTypeAttributeEntityRepository;
    private final TransactionTypeAttributeMapper transactionTypeAttributeMapper;
    private final DataTypeRepositoryPort dataTypeRepositoryPort;


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

    @Transactional
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
