package bhutan.eledger.adapter.in.ref.web.nonworkingdays;

import bhutan.eledger.application.port.in.ref.nonworkingdays.CreateRefNonWorkingDaysUseCase;
import bhutan.eledger.application.port.in.ref.nonworkingdays.ReadRefNonWorkingDaysUseCase;
import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/config/nonworkingdays")
@RequiredArgsConstructor
class RefNonWorkingDaysController {

    private final CreateRefNonWorkingDaysUseCase createRefNonWorkingDaysUseCase;
    private final ReadRefNonWorkingDaysUseCase readRefNonWorkingDaysUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody CreateRefNonWorkingDaysUseCase.CreateRefNonWorkingDaysCommand command) {
        var nonWorkingDays =
                createRefNonWorkingDaysUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nonWorkingDays);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefNonWorkingDays> getAll() {
        return readRefNonWorkingDaysUseCase.readAll();
    }
}
