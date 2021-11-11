package bhutan.eledger.adapter.restapi;

import bhutan.eledger.application.port.in.eledger.transaction.CreateTransactionsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eledger/transaction")
@RequiredArgsConstructor
class TransactionRestController {

    private final CreateTransactionsUseCase createTransactionsUseCase;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateTransactionsUseCase.CreateTransactionsCommand command) {
        createTransactionsUseCase.create(command);

        return ResponseEntity
                .noContent()
                .build();
    }
}
