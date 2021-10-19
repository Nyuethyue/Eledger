package bhutan.eledger.config.glaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import am.iunetworks.lib.multilingual.core.Translation;
import bhutan.eledger.domain.config.glaccount.GLAccountPartType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class GLAccountPartTypeUtils {

    static final Map<Integer, String> LEVEL_TO_DESCRIPTION = Map.of(
            1, "Major group",
            2, "Revenue",
            3, "Revenue-type",
            4, "Revenue sub-type",
            5, "Functional group",
            6, "Functional type",
            7, "Level 7"
    );

    static final Map<Integer, Map<String, String>> LEVEL_TO_PART_TYPE_LANGUAGE_CODE_TO_DESCRIPTION = glAccountPartTypes()
            .stream()
            .collect(Collectors.toMap(
                            GLAccountPartType::getLevel,
                            partType -> partType.getDescription()
                                    .getTranslations()
                                    .stream()
                                    .collect(Collectors.toUnmodifiableMap(
                                                    Translation::getLanguageCode,
                                                    Translation::getValue
                                            )
                                    )
                    )
            );

    private GLAccountPartTypeUtils() {
    }

    static Collection<GLAccountPartType> glAccountPartTypes() {
        return LEVEL_TO_DESCRIPTION
                .keySet()
                .stream()
                .map(GLAccountPartTypeUtils::glAccountPartTypeByLevel)
                .collect(Collectors.toUnmodifiableSet());
    }

    static GLAccountPartType glAccountPartTypeByLevel(Integer level) {
        LocalDateTime creationDateTime = LocalDateTime.now();

        return GLAccountPartType.withoutId(
                level,
                creationDateTime,
                creationDateTime,
                Multilingual.of(
                        Set.of(
                                Translation.of(
                                        "en",
                                        LEVEL_TO_DESCRIPTION.get(level)
                                ),
                                Translation.of(
                                        "dz",
                                        LEVEL_TO_DESCRIPTION.get(level)
                                )
                        )
                )
        );
    }

    static Map<String, String> partTypeLanguageCodeToDescriptionByLevel(Integer level) {
        return LEVEL_TO_PART_TYPE_LANGUAGE_CODE_TO_DESCRIPTION.get(level);
    }


}
