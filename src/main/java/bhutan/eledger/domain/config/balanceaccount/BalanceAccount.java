package bhutan.eledger.domain.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data
public class BalanceAccount {
    private final Long id;
    private final String code;
    private final BalanceAccountPart lastPart;
    private final Multilingual description;
}
