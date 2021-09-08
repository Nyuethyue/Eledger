package bhutan.eledger.domain.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor(staticName = "withId")
public class BalanceAccount {
    private final Long id;
    private final String code;
    private final BalanceAccountStatus status;
    private final LocalDateTime creationDateTime;
    private final LocalDateTime lastModificationDateTime;
    private final ValidityPeriod validityPeriod;
    private final Multilingual description;
    private final Long balanceAccountLastPartId;

    public static BalanceAccount withoutId(
            String code,
            BalanceAccountStatus status,
            LocalDateTime creationDateTime,
            LocalDateTime lastModificationDateTime,
            ValidityPeriod validityPeriod,
            Multilingual description,
            Long balanceAccountLastPartId
    ) {
        return new BalanceAccount(
                null,
                code,
                status,
                creationDateTime,
                lastModificationDateTime,
                validityPeriod,
                description,
                balanceAccountLastPartId
        );
    }
}
