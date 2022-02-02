package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import bhutan.eledger.domain.ref.taxperiodconfig.RefTaxPeriodConfig;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface LoadGenTaxPeriodConfigUseCase {
    RefTaxPeriodConfig loadGen(@Valid LoadGenTaxPeriodConfigCommand command);

    @Data
    class LoadGenTaxPeriodConfigCommand {
        private final String taxTypeCode;
        private final Integer calendarYear;
        private final Long taxPeriodTypeId;
        private final Long transactionTypeId;
    }
}
