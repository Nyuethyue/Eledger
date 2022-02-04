package bhutan.eledger.adapter.in.ref.web.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.CreateRefOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenTaxPeriodConfigUseCase;
import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/ref/openclosetaxperiod")
@RequiredArgsConstructor
class RefOpenCloseTaxPeriodController {
    private final CreateRefOpenCloseTaxPeriodUseCase createRefOpenCloseTaxPeriodUseCase;
    private final LoadGenOpenCloseTaxPeriodUseCase loadGenOpenCloseTaxPeriodUseCase;
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateRefOpenCloseTaxPeriodUseCase.CreateOpenCloseTaxPeriodCommand command) {
        Long id = createRefOpenCloseTaxPeriodUseCase.create(command);
        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }
    @GetMapping(value = "/loadGen", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public RefOpenCloseTaxPeriodConfig loadGen(@RequestBody LoadGenOpenCloseTaxPeriodUseCase.LoadGenOpenCloseTaxPeriodConfigCommand command) {
        return loadGenOpenCloseTaxPeriodUseCase.loadGen(command);
    }


}
