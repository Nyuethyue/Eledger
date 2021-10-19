package bhutan.eledger.application.port.out.config.glaccount;

import bhutan.eledger.domain.config.glaccount.GLAccount;

import java.util.Collection;
import java.util.Optional;

public interface GLAccountRepositoryPort {

    Optional<GLAccount> readById(Long id);

    Collection<GLAccount> readAll();

    Long create(GLAccount glAccount);

    void update(GLAccount glAccount);

    void deleteAll();
}
