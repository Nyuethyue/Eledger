package bhutan.eledger.application.service.ref.holidaydate;

import bhutan.eledger.application.port.in.ref.holidaydate.ReadHolidayDateUseCase;
import bhutan.eledger.application.port.out.ref.denomination.RefDenominationRepositoryPort;
import bhutan.eledger.application.port.out.ref.holidaydate.HolidayDateRepositoryPort;
import bhutan.eledger.domain.ref.holidaydate.HolidayDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class ReadHolidayDateService implements ReadHolidayDateUseCase {
    private final HolidayDateRepositoryPort holidayDateRepositoryPort;

    @Override
    public Collection<HolidayDate> readAll() {
        log.trace("Reading all holiday dates.");

        return holidayDateRepositoryPort.readAll();
    }
}
