package bhutan.eledger.adapter.out.ref.persistence.rrcocahcounters;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface RefRRCOCashCountersEntityRepository extends JpaRepository<RefRRCOCashCountersEntity, Long> {
    @Query(value = "SELECT EXISTS(" +
            "               SELECT" +
            "               FROM ref.rrco_cash_counters" +
            "               WHERE code = :code" +
            "                 AND end_of_validity IS NULL" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.rrco_cash_counters" +
            "               WHERE  code = :code" +
            "                 AND end_of_validity >= :date" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.rrco_cash_counters" +
            "               WHERE code = :code" +
            "                 AND end_of_validity >= :secondDate" +
            "           )", nativeQuery = true)
    boolean existsByCodeAndEndOfValidityNullOrEndOfValidity(String code, LocalDate date, LocalDate secondDate);

    @Query(value = "SELECT  *" +
            " FROM ref.rrco_cash_counters rrco" +
            " WHERE rrco.code = :code" +
            " AND ((rrco.end_of_validity IS NULL AND rrco.start_of_validity<=:currentDate)" +
            " OR (:currentDate BETWEEN rrco.start_of_validity AND rrco.end_of_validity))", nativeQuery = true)
    Optional<RefRRCOCashCountersEntity> readByCode(String code, LocalDate currentDate);
}
