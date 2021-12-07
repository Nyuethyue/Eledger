package bhutan.eledger.adapter.out.eledger.persistence.config.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

interface PropertyEntityRepository extends JpaRepository<PropertyEntity, Long> {
    boolean existsByCode(String code);

    @Query(value = "SELECT EXISTS(" +
            "               SELECT" +
            "               FROM eledger_config.el_property" +
            "               WHERE code = :code" +
            "                 AND end_of_validity IS NULL" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM eledger_config.el_property" +
            "               WHERE  code = :code" +
            "                 AND end_of_validity >= :date" +
            "    UNION ALL" +
            "               SELECT" +
            "               FROM eledger_config.el_property" +
            "               WHERE code = :code" +
            "                 AND end_of_validity >= :secondDate" +
            "           )", nativeQuery = true)
    boolean existsByCodeAndEndOfValidityNullOrEndOfValidityGeAndEndOfValidityGe(String code, LocalDate date, LocalDate secondDate);
}
