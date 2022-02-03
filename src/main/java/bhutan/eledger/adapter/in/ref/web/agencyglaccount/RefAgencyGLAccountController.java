package bhutan.eledger.adapter.in.ref.web.agencyglaccount;

import bhutan.eledger.application.port.in.ref.agencyglaccount.CreateRefAgencyGLAccountUseCase;
import bhutan.eledger.application.port.in.ref.agencyglaccount.ReadRefAgencyGLAccountUseCase;
import bhutan.eledger.domain.ref.agencyglaccount.RefAgencyGLAccount;
import bhutan.eledger.domain.ref.bankaccount.RefBankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ref/agencyglaccount")
@RequiredArgsConstructor
class RefAgencyGLAccountController {
    private final CreateRefAgencyGLAccountUseCase createRefAgencyGlAccountUseCase;
    private final ReadRefAgencyGLAccountUseCase readRefAgencyGLAccountUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody CreateRefAgencyGLAccountUseCase.CreateAgencyGlAccountCommand command) {
        var agencyGLAccounts =
                createRefAgencyGlAccountUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(agencyGLAccounts);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefAgencyGLAccount> getAll() {
        return readRefAgencyGLAccountUseCase.readAll();
    }
}
