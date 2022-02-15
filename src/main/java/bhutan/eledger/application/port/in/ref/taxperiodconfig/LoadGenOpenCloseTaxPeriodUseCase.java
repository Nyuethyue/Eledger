package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriod;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
public interface LoadGenOpenCloseTaxPeriodUseCase {
    RefOpenCloseTaxPeriod loadGen(@Valid LoadGenOpenCloseTaxPeriodUseCase.LoadGenOpenCloseTaxPeriodConfigCommand command);

    @Data
    class LoadGenOpenCloseTaxPeriodConfigCommand {
        @NotNull
        @NotEmpty
        private final String glAccountPartFullCode;

        @NotNull
        @Positive
        private final Integer calendarYear;

        @NotNull
        private final String taxPeriodCode;

        @NotNull
        private final Long transactionTypeId;

        private final Integer years;
        private final Integer month;
    }
}
