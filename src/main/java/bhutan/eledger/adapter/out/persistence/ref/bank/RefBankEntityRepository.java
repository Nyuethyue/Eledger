package bhutan.eledger.adapter.out.persistence.ref.bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

interface RefBankEntityRepository extends JpaRepository<RefBankEntity, Long> {

    boolean existsByCode(String code);

    Optional<RefBankEntity> findByCode(String code);

    @Query(value = "SELECT EXISTS(" +
            "               SELECT" +
            "               FROM ref.bank" +
            "               WHERE code = :code" +
            "                 AND end_of_validity IS NULL" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.bank" +
            "               WHERE  code = :code" +
            "                 AND end_of_validity >= :date" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.bank" +
            "               WHERE code = :code" +
            "                 AND end_of_validity >= :secondDate" +
            "           )", nativeQuery = true)
    boolean existsByCodeAndEndOfValidityNullOrEndOfValidity(String code, LocalDate date, LocalDate secondDate);


}
