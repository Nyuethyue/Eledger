package bhutan.eledger.application.port.out.ref.bankbranch;

import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import java.util.Collection;
import java.util.Optional;

public interface RefBankBranchRepositoryPort {

    Long create(RefBankBranch refBankBranch);

    Collection<RefBankBranch> readAll();

    void deleteAll();

    Optional<RefBankBranch> readById(Long id);

    boolean existsByCode(String code);

    Collection<RefBankBranch> readAllByBankId(Long bankId);

    boolean existsById(Long id);
}
