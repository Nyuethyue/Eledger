package bhutan.eledger.adapter.in.ref.web.holidaydate;

import bhutan.eledger.application.port.in.ref.holidaydate.CreateHolidayDateUseCase;
import bhutan.eledger.application.port.in.ref.holidaydate.ReadHolidayDateUseCase;
import bhutan.eledger.domain.ref.holidaydate.HolidayDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/config/holiday/date")
@RequiredArgsConstructor
class HolidayDateController {

    private final CreateHolidayDateUseCase createHolidayDateUseCase;
    private final ReadHolidayDateUseCase readHolidayDateUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody CreateHolidayDateUseCase.CreateHolidayDateCommand command) {
        var holidayDates =
                createHolidayDateUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(holidayDates);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<HolidayDate> getAll() {
        return readHolidayDateUseCase.readAll();
    }
}
