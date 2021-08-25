package bhutan.eledger.adapter.web.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/config/balance/account")
@RequiredArgsConstructor
class BalanceAccountController {
    private final CreateBalanceAccountUseCase createBalanceAccountUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateBalanceAccountUseCase.CreateBalanceAccountCommand command) {
        Long id = createBalanceAccountUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }
}
