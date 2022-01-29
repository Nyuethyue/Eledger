package bhutan.eledger.application.service.ref.holidaydate;

import bhutan.eledger.application.port.in.ref.holidaydate.ReadRefHolidayDateUseCase;
import bhutan.eledger.application.port.out.ref.holidaydate.RefHolidayDateRepositoryPort;
import bhutan.eledger.domain.ref.holidaydate.RefHolidayDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadHolidayDateService implements ReadRefHolidayDateUseCase {
    private final RefHolidayDateRepositoryPort holidayDateRepositoryPort;

    @Override
    public Collection<RefHolidayDate> readAll() {
        log.trace("Reading all holiday dates.");

        return holidayDateRepositoryPort.readAll();
    }
}
