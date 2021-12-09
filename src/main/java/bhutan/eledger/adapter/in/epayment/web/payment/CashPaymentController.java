package bhutan.eledger.adapter.in.epayment.web.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreateCashMultiplePaymentsUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreateCashPaymentUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreateChequePaymentUseCase;
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

    private final CreateCashPaymentUseCase createCashPaymentUseCase;
    private final CreateCashMultiplePaymentsUseCase createCashMultiplePaymentsUseCase;
    private final CreateChequePaymentUseCase createChequePaymentUseCase;

    @PostMapping("/cash")
    public ResponseEntity<Object> create(@RequestBody CreateCashPaymentUseCase.CreateCashPaymentCommand command) {
        var receipt = createCashPaymentUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipt);
    }

    @PostMapping("/cheque")
    public ResponseEntity<Object> create(@RequestBody CreateChequePaymentUseCase.CreateChequePaymentCommand command) {
        var receipt = createChequePaymentUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipt);
    }

    @PostMapping("/cash/multiple")
    public ResponseEntity<Object> createMultiple(@RequestBody CreateCashMultiplePaymentsUseCase.CreateCashMultiplePaymentsCommand command) {
        var receipts = createCashMultiplePaymentsUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipts);
    }
}
