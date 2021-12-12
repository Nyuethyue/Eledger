package bhutan.eledger.adapter.in.ref.web.denomination;

import bhutan.eledger.application.port.in.ref.denomination.ReadRefDenominationUseCase;
import bhutan.eledger.domain.ref.denomination.RefDenomination;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/ref/denomination")
@RequiredArgsConstructor
class DenominationController {

    private final ReadRefDenominationUseCase readRefDenominationUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefDenomination> getAll() {
        return readRefDenominationUseCase.readAll();
    }
}
