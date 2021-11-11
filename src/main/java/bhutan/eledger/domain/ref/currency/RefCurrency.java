package bhutan.eledger.domain.ref.currency;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;


@Data(staticConstructor = "withId")
public class RefCurrency {
    private final Long id;
    private final String code;
    private final Multilingual description;
}
