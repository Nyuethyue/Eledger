package bhutan.eledger.adapter.in.ref.web.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadTaxPeriodSegmentsUseCase;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodSegment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("/ref/taxperiod/segment")
@RequiredArgsConstructor
class RefTaxPeriodSegmentController {

    private final LoadTaxPeriodSegmentsUseCase loadTaxPeriodSegmentsUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Collection<RefTaxPeriodSegment> findByTaxPeriodTypeId(@NotNull Long taxPeriodTypeId) {
        return loadTaxPeriodSegmentsUseCase.findByTaxPeriodTypeId(taxPeriodTypeId);
    }
}
