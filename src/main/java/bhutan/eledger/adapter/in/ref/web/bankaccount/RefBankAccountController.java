package bhutan.eledger.adapter.in.ref.web.bankaccount;

import bhutan.eledger.application.port.in.ref.bankaccount.CreateRefBankAccountUseCase;
import bhutan.eledger.application.port.in.ref.bankaccount.ReadRefBankAccountUseCase;
import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/ref/bankaccount")
@RequiredArgsConstructor
class RefBankAccountController {

    private final CreateRefBankAccountUseCase createRefBankAccountUseCase;
    private final ReadRefBankAccountUseCase readRefBankAccountUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateRefBankAccountUseCase.CreateBankAccountCommand command) {
        Long id = createRefBankAccountUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefBankAccount> getAll() {
        return readRefBankAccountUseCase.readAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public RefBankAccount getById(@PathVariable Long id) {
        return readRefBankAccountUseCase.readById(id);
    }

    @GetMapping(value = "/allByBranchId/{branchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefBankAccount> readAllByBranchId(@PathVariable Long branchId) {
        return readRefBankAccountUseCase.readAllByBranchId(branchId);
    }

    @GetMapping(value = "/readByCode/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public RefBankAccount readByCode(@PathVariable String code) {
        return readRefBankAccountUseCase.readByCode(code);
    }

    @GetMapping(value = "/readPrimaryAccByGlCode/{glCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public RefBankAccount readPrimaryAccByGlCode(@PathVariable String glCode) {
        return readRefBankAccountUseCase.readPrimaryAccByGlCodeAndFlag(glCode, true);
    }
}
