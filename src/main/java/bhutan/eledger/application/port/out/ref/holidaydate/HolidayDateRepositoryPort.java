package bhutan.eledger.application.port.out.ref.holidaydate;

import bhutan.eledger.domain.ref.holidaydate.HolidayDate;

import java.util.Collection;
import java.util.Optional;

public interface HolidayDateRepositoryPort {
    Collection<HolidayDate> create(Collection<HolidayDate> holidayDates);

    Collection<HolidayDate> readAll();

    void deleteAll();

    Optional<HolidayDate> readById(Long id);

}
