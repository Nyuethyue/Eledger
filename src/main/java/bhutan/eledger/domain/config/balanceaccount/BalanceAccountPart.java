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
    private final ValidityPeriod validityPeriod;
    private final Multilingual description;
    private final Integer balanceAccountPartLevelId;

    public static BalanceAccountPart withoutId(
            String code,
            Long parentId,
            BalanceAccountPartStatus status,
            LocalDateTime creationDateTime,
            LocalDateTime lastModificationDateTime,
            ValidityPeriod validityPeriod,
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
                validityPeriod,
                description,
                balanceAccountPartLevelId
        );
    }

    public LocalDateTime getActualDateTime() {
        switch (status) {
            case ACTIVE:
                return validityPeriod.getStart();
            case INACTIVE:
                return validityPeriod.getEnd();
            default:
                throw new IllegalStateException("Unknown status: " + status);
        }
    }
}
