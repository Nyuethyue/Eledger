package bhutan.eledger.application.port.in.epayment.deposit;

import bhutan.eledger.domain.epayment.deposit.Deposit;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
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

    @Getter
    @ToString
    class CreateDepositMultipleCommand {
        @Valid
        @NotNull
        @NotEmpty
        private final Collection<CreateDepositCommand> deposits;

        @JsonCreator
        public CreateDepositMultipleCommand(Collection<CreateDepositCommand> deposits) {
            this.deposits = deposits;
        }
    }
}