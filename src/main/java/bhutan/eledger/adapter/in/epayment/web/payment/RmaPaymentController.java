package bhutan.eledger.adapter.in.epayment.web.payment;

import bhutan.eledger.application.port.in.epayment.payment.rma.CreateRmaMessageUseCase;
import bhutan.eledger.application.port.in.epayment.payment.rma.InitiateRmaPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/rma")
class RmaPaymentController {
    private final InitiateRmaPaymentUseCase initiateRmaPaymentUseCase;
    private final CreateRmaMessageUseCase createRmaMessageUseCase;

    @PostMapping("/message")
    public ResponseEntity<Object> createMessage(@RequestBody CreateRmaMessageUseCase.CreateRmaMessageCommand command) {
        var rmaMessage = createRmaMessageUseCase.create(command);

        var responseBody = rmaMessage.getArPart().getBfsNameValuePair()
                .entrySet()
                .stream()
                .collect(
                        ArrayList::new,
                        (list, entry) -> list.add(Map.of("id", entry.getKey(), "value", entry.getValue())),
                        Collection::addAll
                );

        return ResponseEntity
                .ok(responseBody);
    }

    @PostMapping("/initiate")
    public ResponseEntity<Object> initiate(@RequestBody InitiateRmaPaymentUseCase.InitiateRmaPaymentCommand command) {
        initiateRmaPaymentUseCase.initiate(command);

        return ResponseEntity
                .noContent()
                .build();
    }
}
