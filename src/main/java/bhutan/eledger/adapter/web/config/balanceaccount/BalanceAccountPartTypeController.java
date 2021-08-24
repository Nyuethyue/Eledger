package bhutan.eledger.adapter.web.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/config/balance/account/part/type")
@RequiredArgsConstructor
public class BalanceAccountPartTypeController {
    private final CreateBalanceAccountPartTypeUseCase createBalanceAccountPartTypeUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateBalanceAccountPartTypeUseCase.CreateBalanceAccountPartTypeCommand command) {
        Integer id = createBalanceAccountPartTypeUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }
}
