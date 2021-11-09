package bhutan.eledger.adapter.web.eledger.config.transaction;

import bhutan.eledger.application.port.in.eledger.config.transaction.CreateTransactionTypeAttributeUseCase;
import bhutan.eledger.application.port.in.eledger.config.transaction.ReadTransactionTypeAttributeUseCase;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/config/transaction/type/attribute")
@RequiredArgsConstructor
class TransactionTypeAttributeController {

    private final CreateTransactionTypeAttributeUseCase createTransactionTypeAttributeUseCase;
    private final ReadTransactionTypeAttributeUseCase readTransactionTypeAttributeUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateTransactionTypeAttributeUseCase.CreateTransactionTypeAttributesCommand command) {
        var transactionTypeAttributes = createTransactionTypeAttributeUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(transactionTypeAttributes);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public TransactionTypeAttribute getById(@PathVariable Long id) {
        return readTransactionTypeAttributeUseCase.readById(id);
    }

    @GetMapping(value = "/getAllByTransactionType/{transactionTypeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<TransactionTypeAttribute> getAllByTransactionTypeId(@PathVariable Long transactionTypeId) {
        return readTransactionTypeAttributeUseCase.readAllByTransactionTypeId(transactionTypeId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<TransactionTypeAttribute> getAll() {
        return readTransactionTypeAttributeUseCase.readAll();
    }
}
