package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

interface RefTaxPeriodTypeEntityRepository extends JpaRepository<RefTaxPeriodType, Long> {
    @Query(value = "SELECT  *" +
            " FROM ref.tax_period_segment tps" +
            " WHERE tps.tax_period_id = :taxPeriodTypeId", nativeQuery = true)
    Collection<RefTaxPeriodSegment> readAllTaxPeriodTypeId(Long taxPeriodTypeId);

}
