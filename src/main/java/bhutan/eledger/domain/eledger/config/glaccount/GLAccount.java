package bhutan.eledger.domain.eledger.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@RequiredArgsConstructor(staticName = "withId")
public class GLAccount {
    private final Long id;
    private final String code;
    private final GLAccountStatus status;
    private final LocalDateTime creationDateTime;
    private final LocalDateTime lastModificationDateTime;
    private final ValidityPeriod validityPeriod;
    private final Multilingual description;
    private final Long glAccountLastPartId;

    public static GLAccount withoutId(
            String code,
            GLAccountStatus status,
            LocalDateTime creationDateTime,
            LocalDateTime lastModificationDateTime,
            ValidityPeriod validityPeriod,
            Multilingual description,
            Long glAccountLastPartId
    ) {
        return new GLAccount(
                null,
                code,
                status,
                creationDateTime,
                lastModificationDateTime,
                validityPeriod,
                description,
                glAccountLastPartId
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
