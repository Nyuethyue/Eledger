package bhutan.eledger.adapter.in.web.epayment.generatereceipt;

import bhutan.eledger.application.port.in.epayment.generatereceipt.GenerateCashReceiptUseCase;
import bhutan.eledger.domain.epayment.generatereceipt.Receipt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment/receipt")
@RequiredArgsConstructor
public class ReceiptController {

    private final GenerateCashReceiptUseCase generateCashReceiptUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody GenerateCashReceiptUseCase.GenerateCashReceiptCommand command) {
        Receipt receipt = generateCashReceiptUseCase.generate(command);

        return ResponseEntity
                .ok(receipt);
    }
}
