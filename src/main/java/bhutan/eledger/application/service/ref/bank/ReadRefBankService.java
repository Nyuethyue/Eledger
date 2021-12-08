package bhutan.eledger.application.service.ref.bank;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.application.port.in.ref.bank.ReadRefBankUseCase;
import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
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
class ReadRefBankService implements ReadRefBankUseCase {
    private final RefBankRepositoryPort refBankRepositoryPort;

    @Override
    public Collection<RefBank> readAll() {
        log.trace("Reading all bank list.");

        return refBankRepositoryPort.readAll();
    }

    @Override
    public RefBank readById(Long id) {
        log.trace("Reading bank by id: {}", id);

        return refBankRepositoryPort.readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("Bank by id: [" + id + "] not found.")
                );
    }

    @Override
    public Collection<RefBank> getOpenBankListByGlPartFullCode(String glPartFullCode) {
        log.trace("Reading all bank list by gl part full code.");
        return refBankRepositoryPort.getBankListByGlPartFullCode(glPartFullCode, LocalDate.now());
    }
}
