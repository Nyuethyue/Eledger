package bhutan.eledger.domain.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor(staticName = "withId")
public class BalanceAccount {
    private final Long id;
    private final String code;
    private final Long balanceAccountLastPartId;
    private final Multilingual description;

    public static BalanceAccount withoutId(String code, Long balanceAccountLastPartId, Multilingual description) {
        return new BalanceAccount(null, code, balanceAccountLastPartId, description);
    }
}
