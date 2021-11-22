package bhutan.eledger.adapter.out.persistence.ref.bankbranch;

import org.springframework.data.jpa.repository.JpaRepository;

interface RefBankBranchRepository extends JpaRepository<RefBankBranchEntity, Long> {
    boolean existsByCode(String code);

    boolean existsByBfscCode(String bfscCode);
}
