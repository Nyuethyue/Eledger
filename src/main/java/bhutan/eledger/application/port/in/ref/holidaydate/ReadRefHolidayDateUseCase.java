package bhutan.eledger.application.port.in.ref.holidaydate;

import bhutan.eledger.domain.ref.holidaydate.RefHolidayDate;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface ReadRefHolidayDateUseCase {
    Collection<RefHolidayDate> readAll();

}
