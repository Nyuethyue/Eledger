package bhutan.eledger.adapter.in.web.epayment.generatereceipt;

import bhutan.eledger.application.port.in.epayment.generatereceipt.GenerateCashReceiptUseCase;
import bhutan.eledger.application.port.in.epayment.generatereceipt.GenerateCashReceiptsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment/receipt/cash")
@RequiredArgsConstructor
public class CashReceiptController {

    private final GenerateCashReceiptUseCase generateCashReceiptUseCase;
    private final GenerateCashReceiptsUseCase generateCashReceiptsUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody GenerateCashReceiptUseCase.GenerateCashReceiptCommand command) {
        var receipt = generateCashReceiptUseCase.generate(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipt);
    }

    @PostMapping("/multiple")
    public ResponseEntity<Object> createSeveral(@RequestBody GenerateCashReceiptsUseCase.GenerateCashReceiptsCommand command) {
        var receipts = generateCashReceiptsUseCase.generate(command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(receipts);
    }
}
