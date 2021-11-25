package bhutan.eledger.application.port.out.eledger.config.transaction;

import bhutan.eledger.domain.eledger.config.transaction.TransactionType;

import java.util.Collection;
import java.util.Optional;

public interface TransactionTypeRepositoryPort {

    Long create(TransactionType transactionType);

    boolean existsByName(String name);

    void deleteAll();

    Optional<TransactionType> readById(Long id);

    Optional<TransactionType> readByName(String name);

    Collection<TransactionType> readAll();
}
