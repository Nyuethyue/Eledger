package bhutan.eledger.adapter.in.ref.web.taxperiodconfig;

import bhutan.eledger.application.port.in.ref.taxperiodconfig.LoadGenOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.SearchOpenCloseTaxPeriodUseCase;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.UpsertRefOpenCloseTaxPeriodUseCase;
import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ref/openclosetaxperiod")
@RequiredArgsConstructor
class RefOpenCloseTaxPeriodController {
    private final UpsertRefOpenCloseTaxPeriodUseCase upsertRefOpenCloseTaxPeriodUseCase;
    private final LoadGenOpenCloseTaxPeriodUseCase loadGenOpenCloseTaxPeriodUseCase;
    private final SearchOpenCloseTaxPeriodUseCase searchOpenCloseTaxPeriodUseCase;

    @PostMapping("/upsert")
    public void create(@RequestBody UpsertRefOpenCloseTaxPeriodUseCase.UpsertOpenCloseTaxPeriodCommand command) {
        upsertRefOpenCloseTaxPeriodUseCase.upsert(command);
    }

    @GetMapping(value = "/loadGen", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public RefOpenCloseTaxPeriod loadGen(LoadGenOpenCloseTaxPeriodUseCase.LoadGenOpenCloseTaxPeriodConfigCommand command) {
        return loadGenOpenCloseTaxPeriodUseCase.loadGen(command);
    }

    @GetMapping(value = "/searchDetails", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public RefOpenCloseTaxPeriod searchDetails(SearchOpenCloseTaxPeriodUseCase.OpenCloseTaxPeriodConfigCommand command) {
        return searchOpenCloseTaxPeriodUseCase.search(command);
    }



}