package bhutan.eledger.application.port.in.epayment.paymentadvice;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Validated
public interface CreatePaymentAdviceUseCase {

    Long create(@Valid CreatePaymentAdviceCommand command);

    @Data
    class CreatePaymentAdviceCommand {
        @NotNull
        @NotEmpty
        private final String drn;

        @Valid
        @NotNull
        private final TaxpayerCommand taxpayer;

        @NotNull
        private final LocalDate dueDate;
        @NotNull
        @Valid
        private final PeriodCommand period;
        @NotNull
        @NotEmpty
        @Valid
        private final Collection<PayableLineCommand> payableLines;
    }

    @Data
    class PayableLineCommand {
        @NotNull
        @PositiveOrZero
        private final BigDecimal amount;
        @NotNull
        @Valid
        private final GLAccountCommand glAccount;
        @NotNull
        private final Long transactionId;
    }

    @Data
    class GLAccountCommand {
        @NotNull
        private final String code;

        @NotNull
        @NotEmpty
        private final Map<String, String> descriptions;
    }

    @Data
    class PeriodCommand {
        @NotNull
        private final String year;
        @NotNull
        private final String segment;
    }

    @Data
    class TaxpayerCommand {
        @NotNull
        @NotEmpty
        private final String tpn;

        @NotNull
        @NotEmpty
        private final String name;
    }
}
