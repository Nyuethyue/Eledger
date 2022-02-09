package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.common.ref.taxperiodconfig.TaxPeriodType;
import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.LocalDate;

@Validated
public interface LoadGenTaxPeriodConfigUseCase {
    RefTaxPeriodConfig loadGen(@Valid LoadGenTaxPeriodConfigCommand command);

    @Data
    class LoadGenTaxPeriodConfigCommand {
        private final String taxTypeCode;
        private final Integer calendarYear;
        private final String taxPeriodTypeCode;
        private final Long transactionTypeId;

        private final Integer dueDateCountForReturnFiling;
        private final Integer dueDateCountForPayment;
        private final LocalDate validFrom;
        private final LocalDate validTo;
        private final Boolean considerNonWorkingDays;
    }
}
