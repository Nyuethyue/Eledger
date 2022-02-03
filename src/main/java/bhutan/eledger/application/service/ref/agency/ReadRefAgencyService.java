package bhutan.eledger.application.service.ref.agency;

import bhutan.eledger.application.port.in.ref.agency.ReadRefAgencyUseCase;
import bhutan.eledger.application.port.out.ref.agency.RefAgencyRepositoryPort;
import bhutan.eledger.domain.ref.agency.RefAgency;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadRefAgencyService implements ReadRefAgencyUseCase {
    private final RefAgencyRepositoryPort refAgencyRepositoryPort;

    @Override
    public Collection<RefAgency> readAll() {
        log.trace("Reading all agency list.");

        return refAgencyRepositoryPort.readAll();
    }
}
