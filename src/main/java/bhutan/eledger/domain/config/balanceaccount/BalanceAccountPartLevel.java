package bhutan.eledger.domain.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data(staticConstructor = "withId")
public class BalanceAccountPartLevel implements Comparable<BalanceAccountPartLevel> {
    private final Integer id;
    @EqualsAndHashCode.Include
    private final Integer level;
    private final Multilingual description;

    @Override
    public int compareTo(BalanceAccountPartLevel o) {
        return level.compareTo(o.level);
    }

    public static BalanceAccountPartLevel withoutId(Integer level, Multilingual description) {
        return new BalanceAccountPartLevel(
                null,
                level,
                description
        );
    }
}
