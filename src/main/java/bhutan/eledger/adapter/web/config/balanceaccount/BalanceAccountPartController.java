package bhutan.eledger.adapter.web.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.ReadBalanceAccountPartUseCase;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/config/balance/account/part")
@RequiredArgsConstructor
class BalanceAccountPartController {
    private final CreateBalanceAccountPartUseCase createBalanceAccountPartUseCase;
    private final ReadBalanceAccountPartUseCase readBalanceAccountPartUseCase;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand command) {
        var balanceAccountParts =
                createBalanceAccountPartUseCase.create(command);

        return ResponseEntity
                .ok(balanceAccountParts);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public BalanceAccountPart getById(@PathVariable Long id) {
        return readBalanceAccountPartUseCase.readById(id);
    }

    @GetMapping(value = "/children/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<BalanceAccountPart> getAllByParentId(@PathVariable Long parentId) {
        return readBalanceAccountPartUseCase.readAllByParentId(parentId);
    }
}
