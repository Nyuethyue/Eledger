package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

interface RefTaxPeriodSegmentEntityRepository extends JpaRepository<RefTaxPeriodSegment, Long> {
    @Query(value = "SELECT  *" +
            " FROM ref.tax_period_segment tps" +
            " WHERE tps.tax_period_id = :taxPeriodId", nativeQuery = true)
    Collection<RefTaxPeriodSegment> readAllByTaxPeriodId(Long taxPeriodId);
    List<RefTaxPeriodSegment> findByTaxPeriodTypeIdOrderByCodeAsc(Long taxPeriodTypeId);
}
