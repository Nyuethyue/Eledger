package bhutan.eledger.adapter.in.epayment.web.deposit;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.application.port.in.epayment.deposit.SearchDepositUseCase;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/payment/deposit")
@RequiredArgsConstructor
class DepositController {
    private final CreateDepositUseCase createDepositUseCase;
    private final SearchDepositUseCase searchDepositUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateDepositUseCase.CreateDepositCommand command) {
        Deposit result = createDepositUseCase.create(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateDepositResult(result.getId(), result.getDepositNumber()));
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<Deposit> search(SearchDepositUseCase.SearchDepositCommand command) {
        return searchDepositUseCase.search(command);
    }

    @Data
    class CreateDepositResult {
        @Valid
        @NotNull
        @NotEmpty
        private final Long depositId;
        @Valid
        @NotNull
        @NotEmpty
        private final String depositNumber;
    }

}
