package bhutan.eledger.adapter.web.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config/balance/account/part")
@RequiredArgsConstructor
public class BalanceAccountPartController {
    private final CreateBalanceAccountPartUseCase createBalanceAccountPartUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand command) {
        var balanceAccountParts =
                createBalanceAccountPartUseCase.create(command);

        return ResponseEntity
                .ok(balanceAccountParts);
    }
}
