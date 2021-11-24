package bhutan.eledger.adapter.out.persistence.ref.bankbranch;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Collection;

interface RefBankBranchRepository extends JpaRepository<RefBankBranchEntity, Long> {
    boolean existsByCode(String code);

    Collection<RefBankBranchEntity> readAllByBankId(Long bankId);
}
