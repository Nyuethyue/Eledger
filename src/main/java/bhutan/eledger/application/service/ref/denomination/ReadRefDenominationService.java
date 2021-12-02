package bhutan.eledger.application.service.ref.denomination;

import bhutan.eledger.application.port.in.ref.denomination.ReadRefDenominationUseCase;
import bhutan.eledger.application.port.out.ref.denomination.RefDenominationRepositoryPort;
import bhutan.eledger.domain.ref.denomination.RefDenomination;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadRefDenominationService implements ReadRefDenominationUseCase {
    private final RefDenominationRepositoryPort defDenominationRepositoryPort;

    @Override
    public Collection<RefDenomination> readAll() {
        log.trace("Reading all Denominations.");

        return defDenominationRepositoryPort.readAll();
    }
}
