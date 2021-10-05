package bhutan.eledger.application.port.out.config.transaction;

import bhutan.eledger.domain.config.transaction.TransactionType;

import java.util.Optional;

public interface TransactionTypeRepositoryPort {

    Long create(TransactionType transactionType);

    boolean existsByName(String name);

    void deleteAll();

    Optional<TransactionType> readById(Long id);
}
