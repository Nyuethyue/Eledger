package bhutan.eledger.domain.eledger.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.common.dto.ValidityPeriod;
import lombok.Data;

import java.time.LocalDateTime;

@Data(staticConstructor = "withId")
public class GLAccountPart {
    private final Long id;
    private final String code;
    private final String fullCode;
    private final Long parentId;
    private final GLAccountPartStatus status;
    private final LocalDateTime creationDateTime;
    private final LocalDateTime lastModificationDateTime;
    private final ValidityPeriod<LocalDateTime> validityPeriod;
    private final Multilingual description;
    private final Integer glAccountPartLevelId;

    public static GLAccountPart withoutId(
            String code,
            String fullCode,
            Long parentId,
            GLAccountPartStatus status,
            LocalDateTime creationDateTime,
            LocalDateTime lastModificationDateTime,
            ValidityPeriod<LocalDateTime> validityPeriod,
            Multilingual description,
            Integer glAccountPartLevelId
    ) {

        return new GLAccountPart(
                null,
                code,
                fullCode,
                parentId,
                status,
                creationDateTime,
                lastModificationDateTime,
                validityPeriod,
                description,
                glAccountPartLevelId
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
