package bhutan.eledger.domain.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data(staticConstructor = "withId")
public class BalanceAccountPart {
    private final Long id;
    private final String code;
    private final Integer balanceAccountPartLevelId;
    private final Multilingual description;

    public static BalanceAccountPart withoutId(String code, Integer balanceAccountPartLevelId, Multilingual description) {

        return new BalanceAccountPart(
                null,
                code,
                balanceAccountPartLevelId,
                description
        );
    }
}
