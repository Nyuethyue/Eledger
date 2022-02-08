package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodTypeRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadTaxPeriodTypesService implements ReadTaxPeriodTypesUseCase {

    private final RefTaxPeriodTypeRepositoryPort refTaxPeriodTypeRepositoryPort;

    @Override
    public Collection<RefTaxPeriodType> readAll() {
        return refTaxPeriodTypeRepositoryPort.readAll();
    }
}
