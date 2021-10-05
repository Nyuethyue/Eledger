package bhutan.eledger.adapter.persistence.config.transaction;

import bhutan.eledger.application.port.out.config.transaction.TransactionTypeRepositoryPort;
import bhutan.eledger.domain.config.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
    public boolean existsByName(String name) {
        return transactionTypeEntityRepository.existsByName(name);
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
}
