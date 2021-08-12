package bhutan.eledger.domain.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class BalanceAccountPartLevel implements Comparable<BalanceAccountPartLevel> {
    private final Long id;
    @EqualsAndHashCode.Exclude
    private final Integer level;
    private final Multilingual description;

    @Override
    public int compareTo(BalanceAccountPartLevel o) {
        return level.compareTo(o.level);
    }
}
