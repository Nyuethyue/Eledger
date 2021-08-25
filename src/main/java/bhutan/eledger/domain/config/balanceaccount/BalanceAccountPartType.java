package bhutan.eledger.domain.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data(staticConstructor = "withId")
public class BalanceAccountPartType implements Comparable<BalanceAccountPartType> {
    private final Integer id;
    @EqualsAndHashCode.Include
    private final Integer level;
    private final LocalDateTime creationDateTime;
    private final LocalDateTime lastModificationDateTime;
    private final Multilingual description;

    @Override
    public int compareTo(BalanceAccountPartType o) {
        return level.compareTo(o.level);
    }

    public static BalanceAccountPartType withoutId(
            Integer level,
            LocalDateTime creationDateTime,
            LocalDateTime lastModificationDateTime,
            Multilingual description
    ) {
        return new BalanceAccountPartType(
                null,
                level,
                creationDateTime,
                lastModificationDateTime,
                description
        );
    }
}
