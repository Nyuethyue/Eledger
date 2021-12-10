package bhutan.eledger.adapter.out.ref.persistence.bankbranch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;

interface RefBankBranchRepository extends JpaRepository<RefBankBranchEntity, Long> {
    boolean existsByCode(String code);

    @Query(value = "SELECT  *" +
            " FROM ref.bank_branch bb" +
            " WHERE bb.bank_id = :bankId" +
            " AND ((bb.end_of_validity IS NULL AND bb.start_of_validity<=:currentDate)" +
            " OR (:currentDate BETWEEN bb.start_of_validity AND bb.end_of_validity))", nativeQuery = true)
    Collection<RefBankBranchEntity> readAllByBankId(Long bankId, LocalDate currentDate);

    @Query(value = "SELECT EXISTS(" +
            "               SELECT" +
            "               FROM ref.bank_branch" +
            "               WHERE code = :code" +
            "                 AND end_of_validity IS NULL" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.bank_branch" +
            "               WHERE  code = :code" +
            "                 AND end_of_validity >= :date" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.bank_branch" +
            "               WHERE code = :code" +
            "                 AND end_of_validity >= :secondDate" +
            "           )", nativeQuery = true)
    boolean existsByCodeAndEndOfValidityNullOrEndOfValidity(String code, LocalDate date, LocalDate secondDate);
}
