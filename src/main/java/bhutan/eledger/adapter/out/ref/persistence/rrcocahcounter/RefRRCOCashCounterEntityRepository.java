package bhutan.eledger.adapter.out.ref.persistence.rrcocahcounter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface RefRRCOCashCounterEntityRepository extends JpaRepository<RefRRCOCashCounterEntity, Long> {
    @Query(value = "SELECT EXISTS(" +
            "               SELECT" +
            "               FROM ref.rrco_cash_counter" +
            "               WHERE code = :code" +
            "                 AND end_of_validity IS NULL" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.rrco_cash_counter" +
            "               WHERE  code = :code" +
            "                 AND end_of_validity >= :date" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM ref.rrco_cash_counter" +
            "               WHERE code = :code" +
            "                 AND end_of_validity >= :secondDate" +
            "           )", nativeQuery = true)
    boolean existsByCodeAndEndOfValidityNullOrEndOfValidity(String code, LocalDate date, LocalDate secondDate);

    @Query(value = "SELECT  *" +
            " FROM ref.rrco_cash_counter rrco" +
            " WHERE rrco.code = :code" +
            " AND ((rrco.end_of_validity IS NULL AND rrco.start_of_validity<=:currentDate)" +
            " OR (:currentDate BETWEEN rrco.start_of_validity AND rrco.end_of_validity))", nativeQuery = true)
    Optional<RefRRCOCashCounterEntity> readByCode(String code, LocalDate currentDate);
}
