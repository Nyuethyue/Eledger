package bhutan.eledger.adapter.in.ref.web.rrcocashcounters;

import bhutan.eledger.application.port.in.ref.rrcocashcounters.CreateRefRRCOCashCountersUseCase;
import bhutan.eledger.application.port.in.ref.rrcocashcounters.ReadRefRRCOCashCountersUseCase;
import bhutan.eledger.domain.ref.rrcocashcounters.RefRRCOCashCounters;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/ref/rrco/cash/counters")
@RequiredArgsConstructor
class RefRRCOCashCountersController {

    private final CreateRefRRCOCashCountersUseCase createRefRRCOCashCountersUseCase;
    private final ReadRefRRCOCashCountersUseCase readRefRRCOCashCountersUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateRefRRCOCashCountersUseCase.CreateRefRRCOCashCountersCommand command) {
        Long id = createRefRRCOCashCountersUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefRRCOCashCounters> getAll() {
        return readRefRRCOCashCountersUseCase.readAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public RefRRCOCashCounters getById(@PathVariable Long id) {
        return readRefRRCOCashCountersUseCase.readById(id);
    }

    @GetMapping(value = "/readByCode/{code}")
    @ResponseStatus(value = HttpStatus.OK)
    public RefRRCOCashCounters readByCode(@PathVariable String code) {
        return readRefRRCOCashCountersUseCase.readByCode(code);
    }
}
