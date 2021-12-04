package bhutan.eledger.adapter.in.web.epayment.payment;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.epayment.payment.SearchReceiptForDetailsUseCase;
import bhutan.eledger.application.port.in.epayment.payment.SearchReceiptUseCase;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/receipt/search")
class SearchReceiptController {

    private final SearchReceiptUseCase searchReceiptUseCase;
    private final SearchReceiptForDetailsUseCase searchReceiptForDetailsUseCase;

    @GetMapping(value = "/slip", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<Receipt> search(SearchReceiptUseCase.SearchReceiptCommand command) {
        return searchReceiptUseCase.search(command);
    }

    @GetMapping(value = "/details",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<Receipt> search(SearchReceiptForDetailsUseCase.SearchReceiptForDetailsCommand command){
        return searchReceiptForDetailsUseCase.search(command);
    }
}
