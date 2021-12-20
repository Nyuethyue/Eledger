package bhutan.eledger.adapter.in.eledger.web.reporting.taxpayeraccount;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.eledger.reporting.taxpayeraccount.SearchTaxpayerAccountUseCase;
import bhutan.eledger.domain.eledger.reporting.taxpayeraccount.TaxpayerAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reporting/taxpayer/account")
class SearchTaxpayerAccountController {

    private final SearchTaxpayerAccountUseCase searchTaxpayerAccountUseCase;

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<TaxpayerAccountDto> search(SearchTaxpayerAccountUseCase.SearchTaxpayerAccountCommand command) {
        return searchTaxpayerAccountUseCase.search(command);
    }
}
