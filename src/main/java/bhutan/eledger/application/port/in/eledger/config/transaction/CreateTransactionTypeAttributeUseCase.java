package bhutan.eledger.application.port.in.eledger.config.transaction;

import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeAttribute;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Map;

@Validated
public interface CreateTransactionTypeAttributeUseCase {

    Collection<TransactionTypeAttribute> create(@Valid CreateTransactionTypeAttributesCommand command);

    @Data
    class CreateTransactionTypeAttributesCommand {
        @NotEmpty
        @Valid
        private final Collection<TransactionTypeAttributeCommand> transactionTypeAttributes;
    }

    @Data
    class TransactionTypeAttributeCommand {
        @NotNull
        private final String code;

        @NotEmpty
        private final Map<String, String> descriptions;

        @NotNull
        @Positive
        private final Integer dataTypeId;
    }
}
