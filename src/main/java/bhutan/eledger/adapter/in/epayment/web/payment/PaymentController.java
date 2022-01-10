package bhutan.eledger.adapter.in.epayment.web.payment;

import bhutan.eledger.application.port.in.epayment.payment.*;
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
class PaymentController {

    private final CreateCashMultiplePaymentsUseCase createCashMultiplePaymentsUseCase;
    private final CreateChequePaymentsUseCase createChequePaymentsUseCase;
    private final CreateCashWarrantPaymentsUseCase createCashWarrantPaymentsUseCase;
    private final CreatePosPaymentsUseCase createPosPaymentsUseCase;
    private final CreateDemandDraftPaymentsUseCase createDemandDraftPaymentsUseCase;

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

    @PostMapping("/cashwarrant/multiple")
    public ResponseEntity<Object> createMultipleCashWarrant(@RequestBody CreateCashWarrantPaymentsUseCase.CreateCashWarrantPaymentsCommand command) {
        var receipts = createCashWarrantPaymentsUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipts);
    }

    @PostMapping("/demanddraft/multiple")
    public ResponseEntity<Object> createMultipleDemandDraft(@RequestBody CreateDemandDraftPaymentsUseCase.CreateDemandDraftPaymentsCommand command) {
        var receipts = createDemandDraftPaymentsUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipts);
    }

    @PostMapping("/pos/multiple")
    public ResponseEntity<Object> createMultiplePos(@RequestBody CreatePosPaymentsUseCase.CreatePosPaymentsCommand command) {
        var receipts = createPosPaymentsUseCase.create(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipts);
    }


}
