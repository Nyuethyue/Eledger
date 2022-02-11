package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

interface RefTaxPeriodSegmentEntityRepository extends JpaRepository<RefTaxPeriodSegment, Long> {
    List<RefTaxPeriodSegment> findByTaxPeriodIdOrderByIdAsc(Long taxPeriodId);
}
