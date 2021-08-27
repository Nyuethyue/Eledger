package bhutan.eledger.adapter.web.config.balanceaccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.SearchBalanceAccountUseCase;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/config/balance/account")
@RequiredArgsConstructor
class BalanceAccountController {
    private final CreateBalanceAccountUseCase createBalanceAccountUseCase;
    private final SearchBalanceAccountUseCase searchBalanceAccountUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateBalanceAccountUseCase.CreateBalanceAccountCommand command) {
        Long id = createBalanceAccountUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<BalanceAccount> search(@RequestBody SearchBalanceAccountUseCase.SearchBalanceAccountCommand command) {
        return searchBalanceAccountUseCase.search(command);
    }
}
