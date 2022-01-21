package bhutan.eledger.adapter.in.epayment.web.paymentadvice;

import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpdatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdviceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/payment/api/paymentadvice")
@RequiredArgsConstructor
class PaymentAdviceController {

    private final CreatePaymentAdviceUseCase createPaymentAdviceUseCase;
    private final UpdatePaymentAdviceUseCase updatePaymentAdviceUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand command) {
        Long id = createPaymentAdviceUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand command) {
        updatePaymentAdviceUseCase.update(id, command);

        return ResponseEntity
                .noContent()
                .build();
    }
}

