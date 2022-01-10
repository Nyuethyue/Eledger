package bhutan.eledger.adapter.in.epayment.web.deposit;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.epayment.deposit.*;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadRecordInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/payment/deposit")
@RequiredArgsConstructor
class DepositController {
    private final CreateDepositUseCase createDepositUseCase;
    private final ApproveReconciliationUseCase updateDepositUseCase;
    private final SearchDepositUseCase searchDepositUseCase;
    private final GenerateReconciliationInfoUseCase generateReconciliationInfoUseCase;
    private final SearchReconciliationUploadHistoryUseCase searchReconciliationUploadHistoryUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateDepositUseCase.CreateDepositCommand command) {
        Deposit result = createDepositUseCase.create(command);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateDepositResult(result.getId(), result.getDepositNumber()));
    }

    @PostMapping("/multiple")
    public ResponseEntity<Object> createMultiple(@RequestBody CreateDepositUseCase.CreateDepositMultipleCommand command) {
        Collection<Deposit> deposits = createDepositUseCase.create(command);
        CreateDepositMultipleResult result =
                new CreateDepositMultipleResult(
                        deposits.stream().map(deposit ->
                                new CreateDepositResult(deposit.getId(), deposit.getDepositNumber()))
                                .collect(Collectors.toUnmodifiableList()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PostMapping("/update/reconciled")
    public ResponseEntity<Object> approveDepositsReconciliation(@RequestBody ApproveReconciliationUseCase.ApproveDepositReconciliationCommand command) {
        updateDepositUseCase.approveDepositReconciliation(command);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<Deposit> search(SearchDepositUseCase.SearchDepositCommand command) {
        return searchDepositUseCase.search(command);
    }

    @GetMapping(value = "/get/reconciliation/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public GenerateReconciliationInfoUseCase.ReconciliationInfo generateReconciliationInfo(GenerateReconciliationInfoUseCase.GenerateDepositReconciliationInfoCommand command) {
        return generateReconciliationInfoUseCase.generate(command);
    }

    @GetMapping(value = "/get/reconciliation/history", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<ReconciliationUploadRecordInfo> searchReconciliationHistoryInfo(
            SearchReconciliationUploadHistoryUseCase.SearchReconciliationUploadRecordCommand command) {
        return searchReconciliationUploadHistoryUseCase.search(command);
    }

    @Data
    static class CreateDepositResult {
        @Valid
        @NotNull
        @NotEmpty
        private final Long depositId;
        @Valid
        @NotNull
        @NotEmpty
        private final String depositNumber;
    }

    @Data
    static class CreateDepositMultipleResult {
        private final Collection<CreateDepositResult> deposits;
    }
}
