package bhutan.eledger.adapter.in.eledger.api.refund;

import bhutan.eledger.application.port.in.eledger.refund.GetRefundDataByTransactionIdsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction/refundable/data")
@RequiredArgsConstructor
class RefundTransactionDataRestController {
    private final GetRefundDataByTransactionIdsUseCase getRefundDataByTransactionIdsUseCase;

    @PostMapping("/load")
    public ResponseEntity<Object> loadByTransactionIds(@RequestBody GetRefundDataByTransactionIdsUseCase.GetRefundDataByTransactionIdsCommand command) {
        var result = getRefundDataByTransactionIdsUseCase.get(command);

        return ResponseEntity
                .ok(result);
    }
}
