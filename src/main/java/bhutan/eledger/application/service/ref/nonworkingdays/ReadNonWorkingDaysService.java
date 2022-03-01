package bhutan.eledger.application.service.ref.nonworkingdays;

import bhutan.eledger.application.port.in.ref.nonworkingdays.ReadRefNonWorkingDaysUseCase;
import bhutan.eledger.application.port.out.ref.nonworkingdays.RefNonWorkingDaysRepositoryPort;
import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;
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
class ReadNonWorkingDaysService implements ReadRefNonWorkingDaysUseCase {
    private final RefNonWorkingDaysRepositoryPort refNonWorkingDaysRepositoryPort;

    @Override
    public Collection<RefNonWorkingDays> readAll() {
        log.trace("Reading all non working days.");

        return refNonWorkingDaysRepositoryPort.readAll();
    }
}
