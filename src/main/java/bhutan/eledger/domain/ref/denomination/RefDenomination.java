package bhutan.eledger.domain.ref.denomination;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data(staticConstructor = "withId")
public class RefDenomination {
    private final Long id;
    private final String code;
    private final String symbol;
    private final Multilingual description;

    public static RefDenomination withoutId(
            String code,
            String symbol,
            Multilingual description
    ) {
        return new RefDenomination(
                null,
                code,
                symbol,
                description
        );
    }
}
