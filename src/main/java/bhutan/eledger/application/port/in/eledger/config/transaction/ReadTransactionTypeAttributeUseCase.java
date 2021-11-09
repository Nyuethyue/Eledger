package bhutan.eledger.application.port.in.eledger.config.transaction;

import bhutan.eledger.domain.eledger.config.transaction.TransactionTypeAttribute;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface ReadTransactionTypeAttributeUseCase {

    TransactionTypeAttribute readById(@NotNull Long id);

    Collection<TransactionTypeAttribute> readAllByTransactionTypeId(@NotNull Long transactionTypeId);

    Collection<TransactionTypeAttribute> readAll();
}
