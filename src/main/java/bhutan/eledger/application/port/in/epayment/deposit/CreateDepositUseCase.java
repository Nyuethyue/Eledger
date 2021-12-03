package bhutan.eledger.application.port.in.epayment.deposit;

import bhutan.eledger.application.port.in.eledger.transaction.CreateTransactionsUseCase;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
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

    Long create(@Valid CreateDepositCommand command);

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
        private final PaymentMode paymentMode;

        @NotNull
        private final DepositStatus status;

        @Valid
        @NotNull
        @NotEmpty
        private final Collection<Long> receipts;
        private final Collection<DenominationCount> denominationCounts;
    }
}
