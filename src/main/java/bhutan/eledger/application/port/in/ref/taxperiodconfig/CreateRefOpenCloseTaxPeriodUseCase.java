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
public interface CreateRefOpenCloseTaxPeriodUseCase {
    Long create(@Valid CreateOpenCloseTaxPeriodCommand command);

    @Data
    class CreateOpenCloseTaxPeriodCommand {

        @NotNull
        @NotEmpty
        private final String taxTypeCode;

        @Valid
        @NotNull
        @PositiveOrZero
        private final Integer calendarYear;

        @NotNull
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
        private Integer periodId;

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
