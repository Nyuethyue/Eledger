package bhutan.eledger.domain.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

@Data
public class BalanceAccountPart {
    private final Long id;
    private final String code;
    private final BalanceAccountPartLevel level;
    private final Multilingual description;
}
