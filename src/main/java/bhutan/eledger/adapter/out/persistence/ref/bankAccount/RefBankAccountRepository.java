package bhutan.eledger.adapter.out.persistence.ref.bankAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

interface RefBankAccountRepository extends JpaRepository<RefBankAccountEntity, Long> {

    Collection<RefBankAccountEntity> readAllByBranchId(Long branchId);

    Optional<RefBankAccountEntity> findByCode(String code);

    @Query(value = "SELECT EXISTS(" +
            "               SELECT" +
            "               FROM ref.bank_account" +
            "               WHERE code = :code" +
            "                 AND end_of_validity IS NULL" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.bank_account" +
            "               WHERE  code = :code" +
            "                 AND end_of_validity >= :date" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.bank_account" +
            "               WHERE code = :code" +
            "                 AND end_of_validity >= :secondDate" +
            "           )", nativeQuery = true)
    boolean existsByCodeAndEndOfValidityNullOrEndOfValidity(String code, LocalDate date, LocalDate secondDate);


    @Modifying
    @Query(value = "update ref.bank_account set is_primary_gl_account = :flag where id = :id", nativeQuery = true)
    void setPrimaryFlagById (Long id,Boolean flag);

    @Query(value = " SELECT A.id" +
            "        FROM ref.bank_account A" +
            "        INNER JOIN ref.bank_account_gl_account_part B" +
            "        ON A.bank_account_gl_account_part_id = B.id" +
            "        WHERE A.branch_id = :branchId" +
            "        AND B.code = :code" +
            "        AND A.is_primary_gl_account = true", nativeQuery = true)
    Long readIdByBranchIdAndGlCode(Long branchId, String code);
}
