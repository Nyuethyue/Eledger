package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import am.iunetworks.lib.common.persistence.spring.querydsl.CustomQuerydslJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

interface RefTaxPeriodConfigEntityRepository extends CustomQuerydslJpaRepository<RefTaxPeriodConfigEntity, Long>, RevisionRepository<RefTaxPeriodConfigEntity, Long, Long> {

    @Query(value = "SELECT tpc.*" +
            " FROM ref.tax_period_config tpc" +
            " WHERE tpc.gl_account_part_full_code = :glAccountPartFullCode" +
            " AND tpc.calendar_year = :calendarYear" +
            " AND tpc.tax_period_code = :taxPeriodCode" +
            " AND tpc.transaction_type_id = :transactionTypeId" +
            " AND tpc.valid_from = :validFrom" +
            " AND (tpc.valid_to IS NULL OR tpc.valid_to = :validTo)"
            , nativeQuery = true)
    Optional<RefTaxPeriodConfigEntity> readBy(
            String glAccountPartFullCode,
            int calendarYear,
            String taxPeriodCode,
            long transactionTypeId,
            LocalDate validFrom,
            LocalDate validTo);

    @Query(value = "SELECT tpc.*" +
            " FROM ref.tax_period_config tpc" +
            " WHERE tpc.gl_account_part_full_code = :glAccountPartFullCode" +
            " AND tpc.calendar_year = :calendarYear" +
            " AND tpc.tax_period_code = :taxPeriodCode" +
            " AND tpc.transaction_type_id = :transactionTypeId"
            , nativeQuery = true)
    Collection<RefTaxPeriodConfigEntity> findAll(String glAccountPartFullCode, int calendarYear, String taxPeriodCode, long transactionTypeId);

}
