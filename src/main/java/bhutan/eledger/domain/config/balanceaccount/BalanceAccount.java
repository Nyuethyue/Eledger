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
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Multilingual description;
    private final Long balanceAccountLastPartId;

    public static BalanceAccount withoutId(
            String code,
            BalanceAccountStatus status,
            LocalDateTime creationDateTime,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Multilingual description,
            Long balanceAccountLastPartId
    ) {
        return new BalanceAccount(
                null,
                code,
                status,
                creationDateTime,
                startDate,
                endDate,
                description,
                balanceAccountLastPartId
        );
    }
}
