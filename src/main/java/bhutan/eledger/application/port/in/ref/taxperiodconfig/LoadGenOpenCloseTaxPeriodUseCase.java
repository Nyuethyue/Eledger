package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiod.RefOpenCloseTaxPeriodConfig;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface LoadGenOpenCloseTaxPeriodUseCase {
    RefOpenCloseTaxPeriodConfig loadGen(@Valid LoadGenOpenCloseTaxPeriodUseCase.LoadGenOpenCloseTaxPeriodConfigCommand command);

    @Data
    class LoadGenOpenCloseTaxPeriodConfigCommand {
        private final String glAccountFullCode;
        @NotNull
        private final Integer calendarYear;
        private final Long taxPeriodTypeId;
        private final Long transactionTypeId;
        private final int years;
        private final int month;
    }
}
