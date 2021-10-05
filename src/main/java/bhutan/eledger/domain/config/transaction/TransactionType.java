package bhutan.eledger.domain.config.transaction;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor(staticName = "withId")
public class TransactionType {
    private final Long id;

    private final String name;

    private final Multilingual description;

    public static TransactionType withoutId(String name, Multilingual description) {
        return new TransactionType(
                null,
                name,
                description
        );
    }
}
