package bhutan.eledger.adapter.in.ref.web.agencyglaccount;

import bhutan.eledger.application.port.in.ref.agencyglaccount.CreateRefAgencyGLAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ref/agencyglaccount")
@RequiredArgsConstructor
class RefAgencyGLAccountController {
    private final CreateRefAgencyGLAccountUseCase createRefAgencyGlAccountUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody CreateRefAgencyGLAccountUseCase.CreateAgencyGlAccountCommand command) {
        var agencyGLAccounts =
                createRefAgencyGlAccountUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(agencyGLAccounts);
    }
}
