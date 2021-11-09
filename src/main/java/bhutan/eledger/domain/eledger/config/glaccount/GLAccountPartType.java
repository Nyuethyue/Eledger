package bhutan.eledger.domain.eledger.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data(staticConstructor = "withId")
public class GLAccountPartType implements Comparable<GLAccountPartType> {
    private final Integer id;
    @EqualsAndHashCode.Include
    private final Integer level;
    private final LocalDateTime creationDateTime;
    private final LocalDateTime lastModificationDateTime;
    private final Multilingual description;

    @Override
    public int compareTo(GLAccountPartType o) {
        return level.compareTo(o.level);
    }

    public static GLAccountPartType withoutId(
            Integer level,
            LocalDateTime creationDateTime,
            LocalDateTime lastModificationDateTime,
            Multilingual description
    ) {
        return new GLAccountPartType(
                null,
                level,
                creationDateTime,
                lastModificationDateTime,
                description
        );
    }
}
