package bhutan.eledger.adapter.in.ref.web.taxperiod;

import bhutan.eledger.application.port.in.ref.taxperiod.UpsertOpenCloseTaxPeriodUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/ref/openclosetaxperiod")
@RequiredArgsConstructor
class RefOpenCloseTaxPeriodController {
    private final UpsertOpenCloseTaxPeriodUseCase upsertOpenCloseTaxPeriodUseCase;
    @PostMapping("/upsert")
    public ResponseEntity<Object> upsert(@RequestBody UpsertOpenCloseTaxPeriodUseCase.UpsertOpenCloseTaxPeriodCommand command) {
        Long id = upsertOpenCloseTaxPeriodUseCase.upsert(command);
        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }
}
