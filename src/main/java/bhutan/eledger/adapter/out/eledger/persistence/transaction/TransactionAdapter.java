package bhutan.eledger.adapter.out.eledger.persistence.transaction;

import bhutan.eledger.application.port.out.eledger.transaction.TransactionRepositoryPort;
import bhutan.eledger.domain.eledger.transaction.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class TransactionAdapter implements TransactionRepositoryPort {
    private final TransactionEntityRepository transactionEntityRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Long create(Transaction transaction) {
        var transactionEntity = transactionEntityRepository.save(
                transactionMapper.mapToEntity(
                        transaction
                )
        );

        return transactionEntity.getId();
    }

    @Override
    public Collection<Long> createAll(Collection<Transaction> transactions) {
        return transactionEntityRepository.saveAllAndFlush(
                        transactions.stream()
                                .map(transactionMapper::mapToEntity)
                                .collect(Collectors.toUnmodifiableList())
                )
                .stream()
                .map(TransactionEntity::getId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Transaction> readById(Long id) {
        return transactionEntityRepository.findById(id)
                .map(transactionMapper::mapToDomain);
    }

    @Override
    public Collection<Transaction> readAll() {
        return transactionEntityRepository.findAll()
                .stream()
                .map(transactionMapper::mapToDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean existsById(Long id) {
        return transactionEntityRepository.existsById(id);
    }

    @Override
    public void deleteAll() {
        transactionEntityRepository.deleteAll();
    }
}
