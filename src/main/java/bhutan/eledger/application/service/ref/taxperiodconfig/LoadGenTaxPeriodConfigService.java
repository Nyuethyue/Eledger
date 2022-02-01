package bhutan.eledger.application.service.ref.taxperiodconfig;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.ref.bank.ReadRefBankUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenTaxPeriodConfigUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import bhutan.eledger.domain.ref.bank.RefBank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LoadGenTaxPeriodConfigService implements LoadGenTaxPeriodConfigUseCase {
    private final RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;

    @Override
    public Collection<RefBank> readAll() {
        log.trace("Reading all bank list.");

        return refTaxPeriodRepositoryPort.readAll();
    }

    @Override
    public RefBank readById(Long id) {
        log.trace("Reading bank by id: {}", id);

        return refTaxPeriodRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("Bank by id: [" + id + "] not found.")
                );
    }
}
