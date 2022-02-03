package bhutan.eledger.application.service.ref.agencyglaccount;

import bhutan.eledger.application.port.in.ref.agencyglaccount.ReadRefAgencyGLAccountUseCase;
import bhutan.eledger.application.port.out.ref.agencyglaccount.RefAgencyGLAccountRepositoryPort;
import bhutan.eledger.domain.ref.agencyglaccount.RefAgencyGLAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadRefAgencyGLAccountService implements ReadRefAgencyGLAccountUseCase {
    private final RefAgencyGLAccountRepositoryPort refAgencyGLAccountRepositoryPort;

    @Override
    public Collection<RefAgencyGLAccount> readAll() {
        log.trace("Reading all agency's gl account list.");

        return refAgencyGLAccountRepositoryPort.readAll();
    }
}
