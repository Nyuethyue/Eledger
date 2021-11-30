package bhutan.eledger.application.port.out.ref.bankaccount;

import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
import java.util.Collection;
import java.util.Optional;

public interface RefBankAccountRepositoryPort {
    Long create(RefBankAccount refBankAccount);

    Collection<RefBankAccount> readAll();

    void deleteAll();

    Optional<RefBankAccount> readById(Long id);

    boolean isOpenBankAccountExists(RefBankAccount refBankAccount);

    Collection<RefBankAccount> readAllByBranchId(Long branchId);

    Optional<RefBankAccount> readByAccNumber(String accNumber);
}
