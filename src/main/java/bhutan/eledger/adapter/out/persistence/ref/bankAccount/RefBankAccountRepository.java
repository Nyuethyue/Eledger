package bhutan.eledger.adapter.out.persistence.ref.bankAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

interface RefBankAccountRepository extends JpaRepository<RefBankAccountEntity,Long> {

    boolean existsByAccNumber(String accNumber);

    Collection<RefBankAccountEntity> readAllByBranchId(Long branchId);

}
