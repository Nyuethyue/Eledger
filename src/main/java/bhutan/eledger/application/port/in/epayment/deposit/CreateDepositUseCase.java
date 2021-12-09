package bhutan.eledger.application.port.in.epayment.deposit;

import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Validated
public interface CreateDepositUseCase {

    Deposit create(@Valid CreateDepositCommand command);
    Collection<Deposit> create(@Valid CreateDepositMultipleCommand command);

    @Data
    class DenominationCountCommand {
        @NotNull
        private final Long denominationId;

        @NotNull
        @Positive
        private final Long denominationCount;
    }

    @Data
    class CreateDepositCommand {
        private final Long paymentMode;
        private final String paymentModeCode;
        private final BigDecimal amount;
        private final LocalDate bankDepositDate;

        @Valid
        @NotNull
        @NotEmpty
        private final Collection<Long> receipts;

        private final Collection<DenominationCountCommand> denominationCounts;
    }

    @Data
    class CreateDepositMultipleCommand {
        private final Collection<CreateDepositCommand> deposits;
    }
}
