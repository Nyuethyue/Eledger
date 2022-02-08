package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodSegmentRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
class RefTaxPeriodSegmentAdapter implements RefTaxPeriodSegmentRepositoryPort {

    private final RefTaxPeriodTypeEntityRepository refTaxPeriodTypeEntityRepository;
    @Override
    public Collection<RefTaxPeriodSegment> loadByTaxPeriodTypeId(Long taxPeriodTypeId) {
        return refTaxPeriodTypeEntityRepository.readAllTaxPeriodTypeId(taxPeriodTypeId);

    }


}
