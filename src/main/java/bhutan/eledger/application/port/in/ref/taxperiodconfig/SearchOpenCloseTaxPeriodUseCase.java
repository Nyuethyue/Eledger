package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefOpenCloseTaxPeriod;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
public interface SearchOpenCloseTaxPeriodUseCase {

    RefOpenCloseTaxPeriod search(@Valid SearchOpenCloseTaxPeriodUseCase.OpenCloseTaxPeriodConfigCommand command);

    @Data
    class OpenCloseTaxPeriodConfigCommand {
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


    }
}
