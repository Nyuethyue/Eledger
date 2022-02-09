package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodTypeRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
@RequiredArgsConstructor
class RefTaxPeriodTypeAdapter implements RefTaxPeriodTypeRepositoryPort {

    private final RefTaxPeriodTypeEntityRepository refTaxPeriodTypeEntityRepository;

    @Override
    public Collection<RefTaxPeriodType> readAll() {
        return refTaxPeriodTypeEntityRepository.findAll();
    }
}
