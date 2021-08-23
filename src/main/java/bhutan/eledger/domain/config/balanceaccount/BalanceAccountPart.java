package bhutan.eledger.domain.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

import java.time.LocalDateTime;

@Data(staticConstructor = "withId")
public class BalanceAccountPart {
    private final Long id;
    private final String code;
    private final Long parentId;
    private final Integer balanceAccountPartLevelId;
    private final BalanceAccountPartStatus status;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Multilingual description;

    public static BalanceAccountPart withoutId(
            String code,
            Long parentId,
            Integer balanceAccountPartLevelId,
            BalanceAccountPartStatus status,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Multilingual description
    ) {

        return new BalanceAccountPart(
                null,
                code,
                parentId,
                balanceAccountPartLevelId,
                status,
                startDate,
                endDate,
                description
        );
    }
}
