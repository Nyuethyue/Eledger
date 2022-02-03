package bhutan.eledger.adapter.in.ref.web.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiod.CreateRefOpenCloseTaxPeriodUseCase;
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
    private final CreateRefOpenCloseTaxPeriodUseCase createRefOpenCloseTaxPeriodUseCase;
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateRefOpenCloseTaxPeriodUseCase.CreateOpenCloseTaxPeriodCommand command) {
        Long id = createRefOpenCloseTaxPeriodUseCase.create(command);
        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

}
