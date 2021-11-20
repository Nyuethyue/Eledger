package bhutan.eledger.adapter.web.ref.bankbranch;

import bhutan.eledger.application.port.in.ref.bankbranch.CreateRefBankBranchUseCase;
import bhutan.eledger.application.port.in.ref.bankbranch.ReadRefBankBranchUseCase;
import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/ref/bankbranch")
@RequiredArgsConstructor
class RefBankBranchController {

    private final CreateRefBankBranchUseCase createRefBankBranchUseCase;
    private final ReadRefBankBranchUseCase readRefBankBranchUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateRefBankBranchUseCase.CreateBranchCommand command) {
        Long id = createRefBankBranchUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefBankBranch> getAll() {
        return readRefBankBranchUseCase.readAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public RefBankBranch getById(@PathVariable Long id) {
        return readRefBankBranchUseCase.readById(id);
    }
}
