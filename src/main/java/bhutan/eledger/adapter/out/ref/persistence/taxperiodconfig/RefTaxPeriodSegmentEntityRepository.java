package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface RefTaxPeriodSegmentEntityRepository extends JpaRepository<RefTaxPeriodSegment, Long> {
    List<RefTaxPeriodSegment> findAllByTaxPeriodIdOrderByIdAsc(Long taxPeriodId);
}
