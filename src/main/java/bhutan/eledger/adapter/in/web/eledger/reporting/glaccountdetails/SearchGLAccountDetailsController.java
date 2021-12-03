package bhutan.eledger.adapter.in.web.eledger.reporting.glaccountdetails;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.eledger.reporting.glaccountdetails.SearchGLAccountDetailsUseCase;
import bhutan.eledger.domain.eledger.reporting.glaccountdetails.GlAccountDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reporting/gl/account/details")
class SearchGLAccountDetailsController {

    private final SearchGLAccountDetailsUseCase searchGLAccountDetailsUseCase;

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public SearchResult<GlAccountDetailsDto> search(SearchGLAccountDetailsUseCase.SearchGLAccountDetailsCommand command) {
        return searchGLAccountDetailsUseCase.search(command);
    }
}
