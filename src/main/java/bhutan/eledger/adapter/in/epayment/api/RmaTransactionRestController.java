package bhutan.eledger.adapter.in.epayment.api;

import bhutan.eledger.application.port.in.epayment.payment.rma.RmaTransactionSuccessUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws/epayment/rma/transaction")
@RequiredArgsConstructor
class RmaTransactionRestController {

    private final RmaTransactionSuccessUseCase rmaTransactionSuccessUseCase;

    @PostMapping("/success")
    public ResponseEntity<Void> create(RmaTransactionSuccessUseCase.RmaTransactionSuccessCommand command) {

        rmaTransactionSuccessUseCase.processSuccess(command);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/failure")
    public ResponseEntity<Void> failure() {

        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/canceled")
    public ResponseEntity<Void> canceled() {

        return ResponseEntity
                .noContent()
                .build();
    }
}
