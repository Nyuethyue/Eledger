package bhutan.eledger.application.port.in.ref.holidaydate;

import bhutan.eledger.domain.ref.holidaydate.HolidayDate;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface ReadHolidayDateUseCase {
    Collection<HolidayDate> readAll();

}
