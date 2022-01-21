package bhutan.eledger.adapter.in.epayment.web.paymentadvice;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchFlatPaymentAdviceByDrnsUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdviceSspUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdviceUseCase;
import bhutan.eledger.domain.epayment.paymentadvice.FlatPaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment/paymentadvice")
@RequiredArgsConstructor
class PaymentAdviceSearchController {

    private final SearchPaymentAdviceUseCase searchPaymentAdviceUseCase;
    private final SearchPaymentAdviceSspUseCase searchPaymentAdviceSspUseCase;
    private final SearchFlatPaymentAdviceByDrnsUseCase searchFlatPaymentAdviceByDrnsUseCase;


    @GetMapping(value = "/search/bits", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<PaymentAdvice> bitsSearch(SearchPaymentAdviceUseCase.SearchPaymentAdviseCommand command) {
        return searchPaymentAdviceUseCase.search(command);
    }

    @GetMapping(value = "/search/ssp", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<PaymentAdvice> sspSearch(SearchPaymentAdviceSspUseCase.SearchPaymentAdviseSspCommand command) {
        return searchPaymentAdviceSspUseCase.search(command);
    }

    @GetMapping(value = "/getPaDrnToPanByDrns", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Map<String, String> getPaDrnToPanByDrns(@RequestBody SearchFlatPaymentAdviceByDrnsUseCase.SearchPaymentAdviceByDrnCommand command) {
        return searchFlatPaymentAdviceByDrnsUseCase
                .searchByDrns(command)
                .stream()
                .collect(Collectors.toMap(FlatPaymentAdvice::getDrn, FlatPaymentAdvice::getPan));
    }
}

