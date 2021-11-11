package bhutan.eledger.adapter.web.ref.currency;

import bhutan.eledger.application.port.in.ref.currency.ReadRefCurrencyUseCase;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/ref/currency")
@RequiredArgsConstructor
class RefCurrencyController {

    private final ReadRefCurrencyUseCase readRefCurrencyUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefCurrency> getAll() {

        return readRefCurrencyUseCase.readAll();
    }
}
