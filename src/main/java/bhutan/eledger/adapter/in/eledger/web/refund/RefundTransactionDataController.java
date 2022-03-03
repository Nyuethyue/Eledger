package bhutan.eledger.adapter.in.eledger.web.refund;

import bhutan.eledger.application.port.in.eledger.refund.GetRefundableDataByTaxTypesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction/refundable/data")
@RequiredArgsConstructor
class RefundTransactionDataController {
    private final GetRefundableDataByTaxTypesUseCase getRefundableDataByTaxTypesUseCase;

    @PostMapping("/load")
    public ResponseEntity<Object> create(@RequestBody GetRefundableDataByTaxTypesUseCase.GetRefundDataByTaxTypesommand command) {
        var result = getRefundableDataByTaxTypesUseCase.get(command);

        return ResponseEntity
                .ok(result);
    }
}
