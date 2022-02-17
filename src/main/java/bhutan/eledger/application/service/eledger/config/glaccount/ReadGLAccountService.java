package bhutan.eledger.application.service.eledger.config.glaccount;

import bhutan.eledger.application.port.in.eledger.config.glaccount.ReadGLAccountUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadGLAccountService implements ReadGLAccountUseCase {
    private final GLAccountRepositoryPort glAccountRepositoryPort;

    @Override
    public Optional<GLAccount> readByCode(String code) {
        log.trace("Reading gl account by code: {}", code);

        return glAccountRepositoryPort.readByCode(code);
    }
}
