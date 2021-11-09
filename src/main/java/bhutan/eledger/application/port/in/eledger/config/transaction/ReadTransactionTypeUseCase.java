package bhutan.eledger.application.port.in.eledger.config.transaction;

import bhutan.eledger.domain.eledger.config.transaction.TransactionType;
import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeWithAttributes;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadTransactionTypeUseCase {

    TransactionType readById(@NotNull Long id);

    TransactionTypeWithAttributes readWithAttributesById(@NotNull Long id);

    Collection<TransactionType> readAll();
}
