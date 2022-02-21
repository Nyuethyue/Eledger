package bhutan.eledger.adapter.in.epayment.web.payment;

import bhutan.eledger.application.port.in.epayment.payment.rma.CreateRmaMessageUseCase;
import bhutan.eledger.application.port.in.epayment.payment.rma.InitiateRmaPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/rma")
class RmaPaymentController {
    private final InitiateRmaPaymentUseCase initiateRmaPaymentUseCase;
    private final CreateRmaMessageUseCase createRmaMessageUseCase;

    @PostMapping("/message")
    public ResponseEntity<Object> createMessage(@RequestBody CreateRmaMessageUseCase.CreateRmaMessageCommand command) {
        var rmaMessage = createRmaMessageUseCase.create(command);

        return ResponseEntity
                .ok(rmaMessage.getArPart().getBfsNameValuePair());
    }

    @PostMapping("/initiate")
    public ResponseEntity<Object> initiate(@RequestBody InitiateRmaPaymentUseCase.InitiateRmaPaymentCommand command) {
        initiateRmaPaymentUseCase.initiate(command);

        return ResponseEntity
                .noContent()
                .build();
    }
}
