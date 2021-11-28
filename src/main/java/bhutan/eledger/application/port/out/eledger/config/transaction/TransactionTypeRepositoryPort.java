package bhutan.eledger.application.port.out.eledger.config.transaction;

import bhutan.eledger.domain.eledger.config.transaction.TransactionType;

import java.util.Collection;
import java.util.Optional;

public interface TransactionTypeRepositoryPort {

    Long create(TransactionType transactionType);

    boolean existsByCode(String code);

    void deleteAll();

    Optional<TransactionType> readById(Long id);

    Optional<TransactionType> readByCode(String code);

    Collection<TransactionType> readAll();
}
