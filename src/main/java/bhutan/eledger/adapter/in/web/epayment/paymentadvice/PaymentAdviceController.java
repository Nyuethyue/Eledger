package bhutan.eledger.adapter.in.web.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdviceUseCase;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/payment/paymentadvice/search")
@RequiredArgsConstructor
public class PaymentAdviceController {

    private final CreatePaymentAdviceUseCase createPaymentAdviceUseCase;
    private final SearchPaymentAdviceUseCase searchPaymentAdviceUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreatePaymentAdviceUseCase.CreatePaymentAdviceCommand command) {
        Long id = createPaymentAdviceUseCase.create(command);

        return ResponseEntity
                .created(URI.create("/" + id))
                .build();
    }

    @GetMapping(value = "/slip", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<PaymentAdvice> search(SearchPaymentAdviceUseCase.SearchPaymentAdviseCommand command) {
        return searchPaymentAdviceUseCase.search(command);
    }
}
