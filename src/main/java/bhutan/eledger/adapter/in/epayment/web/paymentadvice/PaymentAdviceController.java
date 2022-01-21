package bhutan.eledger.adapter.in.epayment.web.paymentadvice;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpdatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdviceUseCase;
import bhutan.eledger.domain.epayment.paymentadvice.FlatPaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment/api/paymentadvice")
@RequiredArgsConstructor
class PaymentAdviceController {

    private final CreatePaymentAdviceUseCase createPaymentAdviceUseCase;
    private final UpdatePaymentAdviceUseCase updatePaymentAdviceUseCase;
    private final SearchPaymentAdviceUseCase searchPaymentAdviceUseCase;

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

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<PaymentAdvice> search(SearchPaymentAdviceUseCase.SearchPaymentAdviseCommand command) {
        return searchPaymentAdviceUseCase.search(command);
    }

    @GetMapping(value = "/getPaDrnToPanByDrns", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, String> getPaDrnToPanByDrns(@RequestBody SearchPaymentAdviceUseCase.SearchPaymentAdviceByDrnCommand command) {
        return searchPaymentAdviceUseCase
                .searchByDrns(command)
                .stream()
                .collect(Collectors.toMap(FlatPaymentAdvice::getDrn,FlatPaymentAdvice::getPan));
    }


}

