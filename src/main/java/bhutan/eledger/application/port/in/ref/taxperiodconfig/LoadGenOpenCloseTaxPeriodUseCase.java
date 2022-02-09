package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriod;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface LoadGenOpenCloseTaxPeriodUseCase {
    RefOpenCloseTaxPeriod loadGen(@Valid LoadGenOpenCloseTaxPeriodUseCase.LoadGenOpenCloseTaxPeriodConfigCommand command);

    @Data
    class LoadGenOpenCloseTaxPeriodConfigCommand {
        private final String glAccountPartFullCode;
        @NotNull
        private final Integer calendarYear;
        private final Long taxPeriodTypeId;
        private final Long transactionTypeId;
        private final Integer years;
        private final Integer month;
    }
}
