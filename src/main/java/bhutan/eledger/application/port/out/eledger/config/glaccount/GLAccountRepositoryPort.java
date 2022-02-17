package bhutan.eledger.application.port.out.eledger.config.glaccount;

import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;

import java.util.Collection;
import java.util.Optional;

public interface GLAccountRepositoryPort {

    Optional<GLAccount> readById(Long id);

    Optional<GLAccount> readByCode(String code);

    Collection<GLAccount> readAll();

    Long create(GLAccount glAccount);

    void update(GLAccount glAccount);

    void deleteAll();

    boolean existsByCode(Collection<String> codes);

}
