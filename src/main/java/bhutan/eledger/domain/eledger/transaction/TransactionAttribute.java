package bhutan.eledger.domain.eledger.transaction;

import lombok.Data;

@Data(staticConstructor = "withId")
public class TransactionAttribute {
    private final Long id;

    private final Long transactionTypeAttributeId;
    private final String value;

    public static TransactionAttribute withoutId(Long transactionTypeAttributeId, String value) {
        return new TransactionAttribute(null, transactionTypeAttributeId, value);
    }
}
