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
    private final LocalDateTime creationDateTime;
    private final LocalDateTime lastModificationDateTime;
    private final Multilingual description;
    private final Long glAccountLastPartId;

    public static GLAccount withoutId(
            String code,
            LocalDateTime creationDateTime,
            LocalDateTime lastModificationDateTime,
            Multilingual description,
            Long glAccountLastPartId
    ) {
        return new GLAccount(
                null,
                code,
                creationDateTime,
                lastModificationDateTime,
                description,
                glAccountLastPartId
        );
    }
}
