package bhutan.eledger.application.port.out.eledger.transaction;

import bhutan.eledger.domain.eledger.transaction.Transaction;

import java.util.Collection;
import java.util.Optional;

public interface TransactionRepositoryPort {

    Long create(Transaction transaction);

    Collection<Long> createAll(Collection<Transaction> transactions);

    Optional<Transaction> readById(Long id);

    Collection<Transaction> readAll();

    boolean existsById(Long id);

    void deleteAll();
}
