package bhutan.eledger.adapter.out.ref.persistence.bankAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

interface RefBankAccountRepository extends JpaRepository<RefBankAccountEntity, Long> {
    @Query(value = "SELECT  *" +
            " FROM ref.bank_account bacc" +
            " WHERE bacc.branch_id = :branchId" +
            " AND ((bacc.end_of_validity IS NULL AND bacc.start_of_validity<=:currentDate)" +
            " OR (:currentDate BETWEEN bacc.start_of_validity AND bacc.end_of_validity))", nativeQuery = true)
    Optional<RefBankAccountEntity> readAllByBranchId(Long branchId,LocalDate currentDate);

    @Query(value = "SELECT  *" +
            " FROM ref.bank_account bacc" +
            " WHERE bacc.code = :code" +
            " AND ((bacc.end_of_validity IS NULL AND bacc.start_of_validity<=:currentDate)" +
            " OR (:currentDate BETWEEN bacc.start_of_validity AND bacc.end_of_validity))", nativeQuery = true)
    Optional<RefBankAccountEntity> readByCode(String code, LocalDate currentDate);

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
    void setPrimaryFlagById(Long id, Boolean flag);

    @Query(value = " SELECT bacc.id" +
            "        FROM ref.bank_account bacc" +
            "        INNER JOIN ref.bank_account_gl_account_part baglacc" +
            "        ON bacc.bank_account_gl_account_part_id = baglacc.id" +
            "        WHERE baglacc.code = :code" +
            "        AND bacc.is_primary_gl_account = :flag", nativeQuery = true)
    Long readIdByGlCodeAndFlag(String code, Boolean flag);
}
