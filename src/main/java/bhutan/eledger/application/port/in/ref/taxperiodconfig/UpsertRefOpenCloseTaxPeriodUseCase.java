package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.Collection;

@Validated
public interface UpsertRefOpenCloseTaxPeriodUseCase {
    void upsert(@Valid UpsertOpenCloseTaxPeriodCommand command);

    @Data
    class UpsertOpenCloseTaxPeriodCommand {
        @NotNull
        @NotEmpty
        private final String glAccountPartFullCode;

        @NotNull
        @PositiveOrZero
        private final Integer calendarYear;

        @NotNull
        private final String taxPeriodCode;

        @NotNull
        private final Long transactionTypeId;

        private final Integer years;

        private final Integer month;

        @NotNull
        @NotEmpty
        @Valid
        private final Collection<OpenCloseTaxPeriodRecordCommand> records;
    }

    @Data
    class OpenCloseTaxPeriodRecordCommand {

        @Valid
        @NotNull
        private Long periodSegmentId;

        @Valid
        @NotNull
        private LocalDate periodOpenDate;

        @Valid
        @NotNull
        private LocalDate periodCloseDate;
    }
}
