package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Validated
public interface LoadGenTaxPeriodConfigUseCase {
    RefTaxPeriodConfig loadGen(@Valid LoadGenTaxPeriodConfigCommand command);

    @Data
    class LoadGenTaxPeriodConfigCommand {
        @NotNull
        @NotEmpty
        private final String taxTypeCode;

        @NotNull
        @Positive
        private final Integer calendarYear;

        @NotNull
        @NotEmpty
        private final String taxPeriodCode;

        @NotNull
        private final Long transactionTypeId;

        @Min(0)
        @Max(364)
        private final Integer dueDateCountForReturnFiling;

        @Min(0)
        @Max(364)
        private final Integer dueDateCountForPayment;
        private final LocalDate validFrom;
        private final LocalDate validTo;
        private final Boolean considerNonWorkingDays;
    }
}
