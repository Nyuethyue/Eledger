package bhutan.eledger.application.port.in.ref.taxperiod;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.Collection;

@Validated
public interface UpsertOpenCloseTaxPeriodUseCase {
    Long upsert(@Valid UpsertOpenCloseTaxPeriodCommand command);

    @Data
    class UpsertOpenCloseTaxPeriodCommand {
        private final Long id;

        @NotNull
        @NotEmpty
        private final String taxTypeCode;

        @Valid
        @NotNull
        @PositiveOrZero
        private final Integer calendarYear;

        @NotNull
        @NotEmpty
        private final Long taxPeriodTypeId;

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
        private String period;

        @Valid
        @NotNull
        private LocalDate periodStart;

        @Valid
        @NotNull
        private LocalDate periodEnd;
    }
}
