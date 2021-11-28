package bhutan.eledger.domain.eledger.config.transaction;

import lombok.Getter;
import lombok.ToString;

import java.util.Collection;

@Getter
@ToString
public class TransactionTypeWithAttributes extends TransactionType {

    private final Collection<TransactionTypeAttribute> attributes;

    TransactionTypeWithAttributes(TransactionType transactionType, Collection<TransactionTypeAttribute> attributes) {
        super(transactionType.getId(), transactionType.getCode(), transactionType.getDescription());
        this.attributes = attributes;
    }
}
