package bhutan.eledger.adapter.out.persistence.eledger.config.transaction;

import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeRepositoryPort;
import bhutan.eledger.domain.eledger.config.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class TransactionTypeRepositoryAdapter implements TransactionTypeRepositoryPort {

    private final TransactionTypeEntityRepository transactionTypeEntityRepository;
    private final TransactionTypeMapper transactionTypeMapper;

    @Override
    public Long create(TransactionType transactionType) {
        TransactionTypeEntity transactionTypeEntity = transactionTypeEntityRepository.save(
                transactionTypeMapper.mapToEntity(transactionType)
        );

        return transactionTypeEntity.getId();
    }

    @Override
    public boolean existsByCode(String code) {
        return transactionTypeEntityRepository.existsByCode(code);
    }

    @Override
    public void deleteAll() {
        transactionTypeEntityRepository.deleteAll();
    }

    @Override
    public Optional<TransactionType> readById(Long id) {
        return transactionTypeEntityRepository.findById(id)
                .map(transactionTypeMapper::mapToDomain);
    }

    @Override
    public Optional<TransactionType> readByCode(String code) {
        return transactionTypeEntityRepository.findByCode(code)
                .map(transactionTypeMapper::mapToDomain);
    }

    @Override
    public Collection<TransactionType> readAll() {
        return transactionTypeEntityRepository.findAll()
                .stream()
                .map(transactionTypeMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }
}
