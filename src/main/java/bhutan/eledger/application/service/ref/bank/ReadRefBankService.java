package bhutan.eledger.application.service.ref.bank;

import bhutan.eledger.application.port.in.ref.bank.ReadRefBankUseCase;
import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
import bhutan.eledger.domain.ref.bank.RefBank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
