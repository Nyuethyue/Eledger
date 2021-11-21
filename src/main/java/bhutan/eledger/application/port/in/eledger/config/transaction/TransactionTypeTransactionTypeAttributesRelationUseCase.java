package bhutan.eledger.application.port.in.eledger.config.transaction;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Collection;

@Validated
public interface TransactionTypeTransactionTypeAttributesRelationUseCase {

    void addTransactionTypeAttributesToTransactionTypeByIds(@NotNull @Positive Long transactionTypeId, TransactionAttributeCommand command);

    void removeTransactionTypeAttributesFromTransactionTypeByIds(@NotNull @Positive Long transactionTypeId, TransactionAttributeCommand command);

    @Data
    class TransactionAttributeCommand {
        private final Collection<Long> transactionTypeAttributeIds;
    }
}
