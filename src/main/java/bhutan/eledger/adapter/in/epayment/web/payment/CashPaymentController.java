package bhutan.eledger.adapter.in.epayment.web.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreateCashMultiplePaymentsUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreateChequePaymentsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/receipt")
class CashPaymentController {

    private final CreateCashMultiplePaymentsUseCase createCashMultiplePaymentsUseCase;
    private final CreateChequePaymentsUseCase createChequePaymentsUseCase;

    @PostMapping("/cheque/multiple")
    public ResponseEntity<Object> createMultipleCheque(@RequestBody CreateChequePaymentsUseCase.CreateChequePaymentsCommand command) {
        var receipt = createChequePaymentsUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipt);
    }

    @PostMapping("/cash/multiple")
    public ResponseEntity<Object> createMultipleCash(@RequestBody CreateCashMultiplePaymentsUseCase.CreateCashMultiplePaymentsCommand command) {
        var receipts = createCashMultiplePaymentsUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipts);
    }
}
