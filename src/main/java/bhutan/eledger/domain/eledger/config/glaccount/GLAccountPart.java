package bhutan.eledger.domain.eledger.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;

import java.time.LocalDateTime;

@Data(staticConstructor = "withId")
public class GLAccountPart {
    private final Long id;
    private final String code;
    private final String fullCode;
    private final Long parentId;
    private final LocalDateTime creationDateTime;
    private final LocalDateTime lastModificationDateTime;
    private final Multilingual description;
    private final Integer glAccountPartLevelId;

    public static GLAccountPart withoutId(
            String code,
            String fullCode,
            Long parentId,
            LocalDateTime creationDateTime,
            LocalDateTime lastModificationDateTime,
            Multilingual description,
            Integer glAccountPartLevelId
    ) {

        return new GLAccountPart(
                null,
                code,
                fullCode,
                parentId,
                creationDateTime,
                lastModificationDateTime,
                description,
                glAccountPartLevelId
        );
    }
}
