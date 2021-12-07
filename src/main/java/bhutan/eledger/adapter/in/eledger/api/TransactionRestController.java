package bhutan.eledger.adapter.in.eledger.api;

import bhutan.eledger.application.port.in.eledger.transaction.CreateTransactionsUseCase;
import bhutan.eledger.application.port.in.eledger.transaction.CreateTransactionsUseCaseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eledger/transaction")
@RequiredArgsConstructor
class TransactionRestController {

    private final CreateTransactionsUseCaseFactory createTransactionsUseCaseFactory;

    @PostMapping("/{service}")
    public ResponseEntity<Void> create(@PathVariable String service, @RequestBody CreateTransactionsUseCase.CreateTransactionsCommand command) {
        createTransactionsUseCaseFactory.get(service).create(command);

        return ResponseEntity
                .noContent()
                .build();
    }
}
