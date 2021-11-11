package bhutan.eledger.application.port.in.eledger.transaction;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public interface CreateTransactionsUseCase {
    void create(@Valid CreateTransactionsUseCase.CreateTransactionsCommand command);

    @Data
    class CreateTransactionsCommand {
        @NotNull
        private final String drn;
        @NotNull
        private final String tpn;

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
        private final Long transactionTypeId;
        @Valid
        private final Collection<TransactionAttributeCommand> transactionAttributeCommands;
    }

    @Data
    class TransactionAttributeCommand {
        @NotNull
        @Positive
        private final Long transactionTypeAttributeId;
        @NotNull
        private final String value;
    }
}
