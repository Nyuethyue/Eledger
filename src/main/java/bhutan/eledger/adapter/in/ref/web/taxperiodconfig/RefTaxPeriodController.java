package bhutan.eledger.adapter.in.ref.web.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenTaxPeriodConfigUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertTaxPeriodUseCase;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/ref/taxperiod")
@RequiredArgsConstructor
class RefTaxPeriodController {

    private final UpsertTaxPeriodUseCase upsertTaxPeriodUseCase;
    private final LoadGenTaxPeriodConfigUseCase loadGenTaxPeriodConfigUseCase;


    @PostMapping("/upsert")
    public ResponseEntity<Object> upsert(@RequestBody UpsertTaxPeriodUseCase.UpsertTaxPeriodCommand command) {
        Long id = upsertTaxPeriodUseCase.upsert(command);
        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(value = "/loadGen", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public RefTaxPeriodConfig loadGen(LoadGenTaxPeriodConfigUseCase.LoadGenTaxPeriodConfigCommand command) {
        return loadGenTaxPeriodConfigUseCase.loadGen(command);
    }
}
