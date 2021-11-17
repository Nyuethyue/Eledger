package bhutan.eledger.application.port.out.ref.bank;

import bhutan.eledger.domain.ref.bank.RefBank;

import java.util.Optional;
import java.util.Collection;

public interface RefBankRepositoryPort {

    Long create(RefBank refBank);

    Collection<RefBank> readAll();

    void deleteAll();

    Optional<RefBank> readById(Long id);

    Optional<RefBank> readByBankName(String bankName);

    Optional<RefBank> readByBfscCode(String bfscCode);

    boolean existByBfscCode(String bfscCode);
}
