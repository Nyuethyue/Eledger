package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface RefTaxPeriodEntityRepository extends JpaRepository<RefTaxPeriodConfigEntity, Long> {

    @Query(value = "SELECT *" +
            " FROM ref.tax_period_config" +
            " WHERE " +
            " calendar_year = :calendarYear" +
            " AND gl_account_part_full_code = :glAccountPartFullCode" +
            " AND tax_period_type_id = :taxPeriodTypeId" +
            " AND transaction_type_id = :transactionTypeId"
            , nativeQuery = true)
    Optional<RefTaxPeriodConfigEntity> readBy(String glAccountPartFullCode, int calendarYear, long taxPeriodTypeId, long transactionTypeId);
}
