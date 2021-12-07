package bhutan.eledger.application.port.in.epayment.deposit;

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

    CreateDepositResult create(@Valid CreateDepositCommand command);

    @Data
    class DenominationCount {
        @NotNull
        private final Long denominationId;

        @NotNull
        @Positive
        private final Long denominationCount;
    }

    @Data
    class CreateDepositCommand {
        private final Long paymentMode;
        private final BigDecimal amount;
        private final LocalDate bankDepositDate;

        @Valid
        @NotNull
        @NotEmpty
        private final Collection<Long> receipts;
        @Valid
        @NotNull
        @NotEmpty
        private final Collection<DenominationCount> denominationCounts;
    }

    @Data
    class CreateDepositResult {
        @Valid
        @NotNull
        @NotEmpty
        private final Long depositId;
        @Valid
        @NotNull
        @NotEmpty
        private final String depositNumber;
    }
}