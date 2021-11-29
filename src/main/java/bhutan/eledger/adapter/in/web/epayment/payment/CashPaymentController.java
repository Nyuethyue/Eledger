package bhutan.eledger.adapter.in.web.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreateCashMultiplePaymentsUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreateCashPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/receipt/cash")
class CashPaymentController {

    private final CreateCashPaymentUseCase createCashPaymentUseCase;
    private final CreateCashMultiplePaymentsUseCase createCashMultiplePaymentsUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateCashPaymentUseCase.CreateCashPaymentCommand command) {
        var receipt = createCashPaymentUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipt);
    }

    @PostMapping("/multiple")
    public ResponseEntity<Object> createMultiple(@RequestBody CreateCashMultiplePaymentsUseCase.CreateCashMultiplePaymentsCommand command) {
        var receipts = createCashMultiplePaymentsUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipts);
    }
}
