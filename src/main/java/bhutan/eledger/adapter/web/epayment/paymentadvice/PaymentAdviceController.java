package bhutan.eledger.adapter.web.epayment.paymentadvice;

import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/payment/paymentadvice")
@RequiredArgsConstructor
public class PaymentAdviceController {

    private final CreatePaymentAdviceUseCase createPaymentAdviceUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreatePaymentAdviceUseCase.CreatePaymentAdviceCommand command) {
        Long id = createPaymentAdviceUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }
}
