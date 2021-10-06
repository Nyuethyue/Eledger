package bhutan.eledger.adapter.persistence.config.transaction;

import bhutan.eledger.application.port.out.config.datatype.DataTypeRepositoryPort;
import bhutan.eledger.application.port.out.config.transaction.TransactionTypeAttributeRepositoryPort;
import bhutan.eledger.domain.config.datatype.DataType;
import bhutan.eledger.domain.config.transaction.TransactionTypeAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class TransactionTypeAttributeRepositoryAdapter implements TransactionTypeAttributeRepositoryPort {

    private final TransactionTypeAttributeEntityRepository transactionTypeAttributeEntityRepository;
    private final TransactionTypeAttributeMapper transactionTypeAttributeMapper;
    private final DataTypeRepositoryPort dataTypeRepositoryPort;

    @Override
    public Long create(TransactionTypeAttribute transactionTypeAttribute) {
        var transactionTypeAttributeEntity = transactionTypeAttributeEntityRepository.save(
                transactionTypeAttributeMapper.mapToEntity(transactionTypeAttribute)
        );

        return transactionTypeAttributeEntity.getId();
    }

    @Override
    public Collection<TransactionTypeAttribute> create(Collection<TransactionTypeAttribute> transactionTypeAttributes) {
        return transactionTypeAttributeEntityRepository.saveAll(
                        transactionTypeAttributes
                                .stream()
                                .map(transactionTypeAttributeMapper::mapToEntity)
                                .collect(Collectors.toList())
                )
                .stream()
                .map(entity -> {
                    DataType dataType = dataTypeRepositoryPort.requiredReadById(entity.getDataTypeId());
                    return transactionTypeAttributeMapper.mapToDomain(entity, dataType);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean existsByAnyName(Collection<String> names) {
        return transactionTypeAttributeEntityRepository.existsByNameIn(names);
    }

    @Override
    public void deleteAll() {
        transactionTypeAttributeEntityRepository.deleteAll();
    }

    @Override
    public Optional<TransactionTypeAttribute> readById(Long id) {
        return transactionTypeAttributeEntityRepository.findById(id)
                .map(entity -> {
                    DataType dataType = dataTypeRepositoryPort.requiredReadById(entity.getDataTypeId());
                    return transactionTypeAttributeMapper.mapToDomain(entity, dataType);
                });
    }
}
