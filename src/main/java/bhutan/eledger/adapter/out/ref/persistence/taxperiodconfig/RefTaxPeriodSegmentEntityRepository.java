package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodType;
import org.springframework.data.jpa.repository.JpaRepository;

interface RefTaxPeriodSegmentEntityRepository extends JpaRepository<RefTaxPeriodSegment, Long> {

}
