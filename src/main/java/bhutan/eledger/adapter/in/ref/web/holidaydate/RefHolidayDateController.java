package bhutan.eledger.adapter.in.ref.web.holidaydate;

import bhutan.eledger.application.port.in.ref.holidaydate.CreateRefHolidayDateUseCase;
import bhutan.eledger.application.port.in.ref.holidaydate.ReadRefHolidayDateUseCase;
import bhutan.eledger.domain.ref.holidaydate.RefHolidayDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/config/holiday/date")
@RequiredArgsConstructor
class RefHolidayDateController {

    private final CreateRefHolidayDateUseCase createRefHolidayDateUseCase;
    private final ReadRefHolidayDateUseCase readRefHolidayDateUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody CreateRefHolidayDateUseCase.CreateRefHolidayDateCommand command) {
        var holidayDates =
                createRefHolidayDateUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(holidayDates);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefHolidayDate> getAll() {
        return readRefHolidayDateUseCase.readAll();
    }
}
