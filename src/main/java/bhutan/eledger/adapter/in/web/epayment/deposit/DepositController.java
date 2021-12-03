package bhutan.eledger.adapter.in.web.epayment.deposit;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.application.port.in.epayment.deposit.SearchDepositUseCase;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/payment/deposit")
@RequiredArgsConstructor
public class DepositController {

    private final CreateDepositUseCase createDepositUseCase;
    private final SearchDepositUseCase searchDepositUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateDepositUseCase.CreateDepositCommand command) {
        Long id = createDepositUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<Deposit> search(SearchDepositUseCase.SearchDepositCommand command) {
        return searchDepositUseCase.search(command);
    }
}
