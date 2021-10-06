package bhutan.eledger.domain.config.transaction;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.domain.config.datatype.DataType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor(staticName = "withId")
public class TransactionTypeAttribute {
    private final Long id;
    private final String name;
    private final DataType dataType;
    private final Multilingual description;

    public static TransactionTypeAttribute withoutId(String name, DataType dataType, Multilingual description) {
        return new TransactionTypeAttribute(
                null,
                name,
                dataType,
                description
        );
    }
}
