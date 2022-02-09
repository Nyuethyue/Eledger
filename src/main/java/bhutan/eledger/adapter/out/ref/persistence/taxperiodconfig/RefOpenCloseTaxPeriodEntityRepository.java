package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface RefOpenCloseTaxPeriodEntityRepository extends JpaRepository<RefOpenCloseTaxPeriodEntity, Long> {
    @Query(value = "SELECT tpc.*" +
            " FROM ref.open_close_tax_period_config tpc" +
            " WHERE tpc.gl_account_part_full_code = :glAccountPartFullCode" +
            " AND tpc.calendar_year = :calendarYear" +
            " AND tpc.tax_period_type_id = :taxPeriodTypeId" +
            " AND tpc.transaction_type_id = :transactionTypeId"
            , nativeQuery = true)
    Optional<RefOpenCloseTaxPeriodEntity> readBy(String glAccountPartFullCode, Integer calendarYear, long taxPeriodTypeId, long transactionTypeId);
}
