package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface CreateRefOpenCloseTaxPeriodUseCase {
    Long create(@Valid UpsertRefOpenCloseTaxPeriodUseCase.UpsertOpenCloseTaxPeriodCommand command);
}
