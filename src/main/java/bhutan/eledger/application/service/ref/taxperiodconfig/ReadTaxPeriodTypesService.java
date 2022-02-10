package bhutan.eledger.application.service.ref.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodTypeRepositoryPort;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriod;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadTaxPeriodTypesService implements ReadTaxPeriodTypesUseCase {

    private final RefTaxPeriodTypeRepositoryPort refTaxPeriodTypeRepositoryPort;

    @Override
    public Collection<RefTaxPeriod> readAll() {
        return refTaxPeriodTypeRepositoryPort.readAll();
    }

    @Override
    public Optional<RefTaxPeriod> readByCode(String code) {
        return refTaxPeriodTypeRepositoryPort.readByCode(code);
    }
}
