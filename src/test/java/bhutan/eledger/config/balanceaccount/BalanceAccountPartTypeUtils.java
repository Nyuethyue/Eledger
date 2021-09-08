package bhutan.eledger.config.balanceaccount;

import am.iunetworks.lib.multilingual.core.Multilingual;
import am.iunetworks.lib.multilingual.core.Translation;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

class BalanceAccountPartTypeUtils {

    static final Map<Integer, String> LEVEL_TO_DESCRIPTION = Map.of(
            1, "Major group",
            2, "Revenue",
            3, "Revenue-type",
            4, "Revenue sub-type",
            5, "Functional group",
            6, "Functional type",
            7, "Level 7"
    );

    static final Map<Integer, Map<String, String>> LEVEL_TO_PART_TYPE_LANGUAGE_CODE_TO_DESCRIPTION = balanceAccountPartTypes()
            .stream()
            .collect(Collectors.toMap(
                            BalanceAccountPartType::getLevel,
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

    private BalanceAccountPartTypeUtils() {
    }

    static Collection<BalanceAccountPartType> balanceAccountPartTypes() {
        return LEVEL_TO_DESCRIPTION
                .keySet()
                .stream()
                .map(BalanceAccountPartTypeUtils::balanceAccountPartTypeByLevel)
                .collect(Collectors.toUnmodifiableSet());
    }

    static BalanceAccountPartType balanceAccountPartTypeByLevel(Integer level) {
        LocalDateTime creationDateTime = LocalDateTime.now();

        return BalanceAccountPartType.withoutId(
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
