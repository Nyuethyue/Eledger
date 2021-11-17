package bhutan.eledger.adapter.web.ref.bank;

import bhutan.eledger.application.port.in.ref.bank.CreateRefBankUseCase;
import bhutan.eledger.application.port.in.ref.bank.ReadRefBankUseCase;
import bhutan.eledger.domain.ref.bank.RefBank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/ref/bank")
@RequiredArgsConstructor
class RefBankController {

    private final CreateRefBankUseCase createRefBankUseCase;
    private final ReadRefBankUseCase readRefBankUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateRefBankUseCase.CreateRefBankCommand command) {
        Long id = createRefBankUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefBank> getAll() {
        return readRefBankUseCase.readAll();
    }
}
