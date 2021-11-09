package bhutan.eledger.application.port.in.epayment.paymentadvice;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

public interface CreatePaymentAdviceUseCase {

    Long create(CreatePaymentAdviceCommand command);

    @Data
    class CreatePaymentAdviceCommand {
        @NotNull
        @NotEmpty
        private final String drn;
        @NotNull
        @NotEmpty
        private final String tpn;
        @NotNull
        private final LocalDate dueDate;
        @NotNull
        @Valid
        private final PeriodCommand period;
        @NotNull
        @NotEmpty
        @Valid
        private final Collection<PaymentLineCommand> paymentLines;
    }

    @Data
    class PaymentLineCommand {
        @NotNull
        @PositiveOrZero
        private final BigDecimal amount;
        @NotNull
        @Valid
        private final GLAccountCommand glAccount;
    }

    @Data
    class GLAccountCommand {
        @NotNull
        private final String code;

        @NotNull
        @NotEmpty
        private final Map<String, String> description;
    }

    @Data
    class PeriodCommand {
        @NotNull
        private final LocalDate start;
        @NotNull
        private final LocalDate end;
    }
}
