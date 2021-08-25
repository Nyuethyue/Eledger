package bhutan.eledger.adapter.web.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.ReadBalanceAccountPartTypeUseCase;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/config/balance/account/part/type")
@RequiredArgsConstructor
class BalanceAccountPartTypeController {
    private final CreateBalanceAccountPartTypeUseCase createBalanceAccountPartTypeUseCase;
    private final ReadBalanceAccountPartTypeUseCase readBalanceAccountPartTypeUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateBalanceAccountPartTypeUseCase.CreateBalanceAccountPartTypeCommand command) {
        Integer id = createBalanceAccountPartTypeUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceAccountPartType getById(@PathVariable Integer id) {
        return readBalanceAccountPartTypeUseCase.readById(id);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<BalanceAccountPartType> getAll() {
        return readBalanceAccountPartTypeUseCase.readAll();
    }
}
