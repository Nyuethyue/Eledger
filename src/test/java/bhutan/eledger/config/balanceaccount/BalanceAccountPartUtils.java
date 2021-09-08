package bhutan.eledger.config.balanceaccount;


import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;

import java.util.List;
import java.util.Map;
import java.util.Set;

class BalanceAccountPartUtils {
    private BalanceAccountPartUtils() {
    }

    static List<Long> createParts(CreateBalanceAccountPartTypeUseCase createBalanceAccountPartTypeUseCase,
                                  CreateBalanceAccountPartUseCase createBalanceAccountPartUseCase,
                                  BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort
    ) {
        BalanceAccountPartTypeUtils.LEVEL_TO_PART_TYPE_LANGUAGE_CODE_TO_DESCRIPTION
                .forEach((level, LanguageCodeToDescription) ->
                        createBalanceAccountPartTypeUseCase.create(
                                new CreateBalanceAccountPartTypeUseCase.CreateBalanceAccountPartTypeCommand(
                                        level,
                                        LanguageCodeToDescription
                                )
                        )
                );

        Long id1 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        null,
                        balanceAccountPartTypeRepositoryPort.readByLevel(1).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "11",
                                        Map.of(
                                                "en", "Revenue"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id2 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        id1,
                        balanceAccountPartTypeRepositoryPort.readByLevel(2).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "1",
                                        Map.of(
                                                "en", "Tax"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id3 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        id2,
                        balanceAccountPartTypeRepositoryPort.readByLevel(3).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "10",
                                        Map.of(
                                                "en", "Taxes on Income"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id4 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        id3,
                        balanceAccountPartTypeRepositoryPort.readByLevel(4).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "2",
                                        Map.of(
                                                "en", "Payable by individual"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id5 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        id4,
                        balanceAccountPartTypeRepositoryPort.readByLevel(5).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "2",
                                        Map.of(
                                                "en", "Personal income tax"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id6 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        id5,
                        balanceAccountPartTypeRepositoryPort.readByLevel(6).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "0",
                                        Map.of(
                                                "en", "Resident"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        return List.of(id3, id1, id6, id2, id5, id4);
    }
}
