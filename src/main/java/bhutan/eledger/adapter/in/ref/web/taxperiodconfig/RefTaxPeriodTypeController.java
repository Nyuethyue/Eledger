package bhutan.eledger.adapter.in.ref.web.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.ReadTaxPeriodTypesUseCase;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/ref/taxperiod/type")
@RequiredArgsConstructor
class RefTaxPeriodTypeController {

    private final ReadTaxPeriodTypesUseCase readTaxPeriodTypesUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefTaxPeriod> readAll() {
        return readTaxPeriodTypesUseCase.readAll();
    }
}
