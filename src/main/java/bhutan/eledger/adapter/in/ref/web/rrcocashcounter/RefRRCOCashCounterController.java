package bhutan.eledger.adapter.in.ref.web.rrcocashcounter;

import bhutan.eledger.application.port.in.ref.rrcocashcounter.CreateRefRRCOCashCountersUseCase;
import bhutan.eledger.application.port.in.ref.rrcocashcounter.ReadRefRRCOCashCounterUseCase;
import bhutan.eledger.domain.ref.rrcocashcounter.RefRRCOCashCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/ref/rrco/cash/counter")
@RequiredArgsConstructor
class RefRRCOCashCounterController {

    private final CreateRefRRCOCashCountersUseCase createRefRRCOCashCountersUseCase;
    private final ReadRefRRCOCashCounterUseCase readRefRRCOCashCounterUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateRefRRCOCashCountersUseCase.CreateRefRRCOCashCountersCommand command) {
        Long id = createRefRRCOCashCountersUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefRRCOCashCounter> getAll() {
        return readRefRRCOCashCounterUseCase.readAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public RefRRCOCashCounter getById(@PathVariable Long id) {
        return readRefRRCOCashCounterUseCase.readById(id);
    }

    @GetMapping(value = "/readByCode/{code}")
    @ResponseStatus(value = HttpStatus.OK)
    public RefRRCOCashCounter readByCode(@PathVariable String code) {
        return readRefRRCOCashCounterUseCase.readByCode(code);
    }
}
