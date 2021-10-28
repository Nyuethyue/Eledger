package bhutan.eledger.adapter.web.config.transaction;

import bhutan.eledger.application.port.in.config.transaction.CreateTransactionTypeUseCase;
import bhutan.eledger.application.port.in.config.transaction.ReadTransactionTypeUseCase;
import bhutan.eledger.application.port.in.config.transaction.TransactionTypeTransactionTypeAttributesRelationUseCase;
import bhutan.eledger.domain.config.transaction.TransactionType;
import bhutan.eledger.domain.config.transaction.TransactionTypeWithAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/config/transaction/type")
@RequiredArgsConstructor
class TransactionTypeController {

    private final CreateTransactionTypeUseCase createTransactionTypeUseCase;
    private final ReadTransactionTypeUseCase readTransactionTypeUseCase;
    private final TransactionTypeTransactionTypeAttributesRelationUseCase transactionTypeTransactionTypeAttributesRelationUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateTransactionTypeUseCase.CreateTransactionTypeCommand command) {
        Long id = createTransactionTypeUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @PutMapping(value = "/addAttributes/{id}")
    public ResponseEntity<Void> add(@PathVariable Long id, @RequestBody TransactionTypeTransactionTypeAttributesRelationUseCase.TransactionAttributeCommand command) {
        transactionTypeTransactionTypeAttributesRelationUseCase.addTransactionTypeAttributesToTransactionTypeByIds(id, command);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping(value = "/removeAttributes/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id, @RequestBody TransactionTypeTransactionTypeAttributesRelationUseCase.TransactionAttributeCommand command) {
        transactionTypeTransactionTypeAttributesRelationUseCase.removeTransactionTypeAttributesFromTransactionTypeByIds(id, command);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public TransactionType getById(@PathVariable Long id) {
        return readTransactionTypeUseCase.readById(id);
    }

    @GetMapping(value = "/getWithAttributes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public TransactionTypeWithAttributes getWithAttributesById(@PathVariable Long id) {
        return readTransactionTypeUseCase.readWithAttributesById(id);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<TransactionType> getAll() {
        return readTransactionTypeUseCase.readAll();
    }

}
