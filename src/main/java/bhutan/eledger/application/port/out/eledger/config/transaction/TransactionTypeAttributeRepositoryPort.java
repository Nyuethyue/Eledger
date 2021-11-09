package bhutan.eledger.application.port.out.eledger.config.transaction;

import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeAttribute;

import java.util.Collection;
import java.util.Optional;

public interface TransactionTypeAttributeRepositoryPort {

    Long create(TransactionTypeAttribute transactionTypeAttribute);

    Collection<TransactionTypeAttribute> create(Collection<TransactionTypeAttribute> transactionTypeAttributes);

    boolean existsByAnyName(Collection<String> names);

    void deleteAll();

    Optional<TransactionTypeAttribute> readById(Long id);

    Collection<TransactionTypeAttribute> readAll();
}
