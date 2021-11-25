package bhutan.eledger.application.port.in.eledger.transaction;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Validated
public interface CreateTransactionsUseCase {
    void create(@Valid CreateTransactionsUseCase.CreateTransactionsCommand command);

    @Data
    class CreateTransactionsCommand {
        @NotNull
        private final String drn;
        @NotNull
        @Valid
        private final TaxpayerCommand taxpayer;
        @Valid
        @NotNull
        @NotEmpty
        private final Collection<TransactionCommand> transactions;
    }

    @Data
    class TransactionCommand {
        @NotNull
        private final String glAccountCode;

        @NotNull
        private final LocalDate settlementDate;
        @NotNull
        @PositiveOrZero
        private final BigDecimal amount;
        @NotNull
        private final String transactionTypeCode;
        @Valid
        private final Collection<TransactionAttributeCommand> transactionAttributes;
    }

    @Data
    class TransactionAttributeCommand {
        @NotNull
        @Positive
        private final String transactionTypeAttributeCode;
        @NotNull
        private final String value;
    }

    @Data
    class TaxpayerCommand {
        private final String tpn;
        private final String name;
    }
}
