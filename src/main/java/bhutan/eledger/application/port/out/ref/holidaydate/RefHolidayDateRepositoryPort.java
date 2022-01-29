package bhutan.eledger.application.port.out.ref.holidaydate;

import bhutan.eledger.domain.ref.holidaydate.RefHolidayDate;

import java.util.Collection;
import java.util.Optional;

public interface RefHolidayDateRepositoryPort {
    Collection<RefHolidayDate> create(Collection<RefHolidayDate> holidayDates);

    Collection<RefHolidayDate> readAll();

    void deleteAll();

    Optional<RefHolidayDate> readById(Long id);

}
