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

    Optional<RefBankAccount> readAllByBranchId(Long branchId);

    Collection<RefBankAccount> readByCode(String code);

    void setPrimaryFlagById(Long id, Boolean flag);

   Long readIdByGlCodeAndFlag(String code, Boolean flag);
}
