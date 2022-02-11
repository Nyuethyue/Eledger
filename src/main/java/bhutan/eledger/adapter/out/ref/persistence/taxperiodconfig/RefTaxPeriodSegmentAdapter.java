package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodSegmentRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class RefTaxPeriodSegmentAdapter implements RefTaxPeriodSegmentRepositoryPort {

    private final RefTaxPeriodSegmentEntityRepository refTaxPeriodSegmentEntityRepository;
    @Override
    public List<RefTaxPeriodSegment> loadByTaxPeriodId(Long taxPeriodId) {
        return refTaxPeriodSegmentEntityRepository.findAllByTaxPeriodIdOrderByIdAsc(taxPeriodId);
    }
}
