package bhutan.eledger.domain.config.transaction;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Collection;

@Getter
@ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionType {
    private final Long id;

    private final String name;

    private final Multilingual description;

    public static TransactionType withId(Long id, String name, Multilingual description) {
        return new TransactionType(
                id,
                name,
                description
        );
    }

    public static TransactionType withoutId(String name, Multilingual description) {
        return new TransactionType(
                null,
                name,
                description
        );
    }

    public TransactionTypeWithAttributes withAttributes(Collection<TransactionTypeAttribute> attributes) {
        return new TransactionTypeWithAttributes(
                this,
                attributes
        );
    }
}
