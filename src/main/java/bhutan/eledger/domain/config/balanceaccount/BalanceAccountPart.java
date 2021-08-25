package bhutan.eledger.domain.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

import java.time.LocalDateTime;

@Data(staticConstructor = "withId")
public class BalanceAccountPart {
    private final Long id;
    private final String code;
    private final Long parentId;
    private final BalanceAccountPartStatus status;
    private final LocalDateTime creationDateTime;
    private final LocalDateTime lastModificationDateTime;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Multilingual description;
    private final Integer balanceAccountPartLevelId;

    public static BalanceAccountPart withoutId(
            String code,
            Long parentId,
            BalanceAccountPartStatus status,
            LocalDateTime creationDateTime,
            LocalDateTime lastModificationDateTime,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Multilingual description,
            Integer balanceAccountPartLevelId
    ) {

        return new BalanceAccountPart(
                null,
                code,
                parentId,
                status,
                creationDateTime,
                lastModificationDateTime,
                startDate,
                endDate,
                description,
                balanceAccountPartLevelId
        );
    }
}
