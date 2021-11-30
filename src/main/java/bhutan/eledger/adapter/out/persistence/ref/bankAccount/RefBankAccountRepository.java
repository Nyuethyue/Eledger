package bhutan.eledger.adapter.out.persistence.ref.bankAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

interface RefBankAccountRepository extends JpaRepository<RefBankAccountEntity,Long> {

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
}
