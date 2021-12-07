package bhutan.eledger.adapter.in.ref.web.currency;

import bhutan.eledger.application.port.in.ref.currency.CreateRefCurrencyUseCase;
import bhutan.eledger.application.port.in.ref.currency.ReadRefCurrencyUseCase;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/ref/currency")
@RequiredArgsConstructor
class RefCurrencyController {

    private final ReadRefCurrencyUseCase readRefCurrencyUseCase;
    private final CreateRefCurrencyUseCase createRefCurrencyUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateRefCurrencyUseCase.CreateCurrencyCommand command) {
        Long id = createRefCurrencyUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefCurrency> getAll() {

        return readRefCurrencyUseCase.readAll();
    }
}
