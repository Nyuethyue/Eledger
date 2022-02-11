package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadTaxPeriodSegmentsUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodSegmentRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadTaxPeriodSegmentsService implements LoadTaxPeriodSegmentsUseCase {

    private final RefTaxPeriodSegmentRepositoryPort refTaxPeriodSegmentRepositoryPort;

    @Override
    public Collection<RefTaxPeriodSegment> findByTaxPeriodId(Long taxPeriodId) {
        return refTaxPeriodSegmentRepositoryPort.loadByTaxPeriodId(taxPeriodId);
    }
}
