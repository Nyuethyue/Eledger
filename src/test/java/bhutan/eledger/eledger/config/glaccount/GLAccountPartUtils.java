package bhutan.eledger.eledger.config.glaccount;


import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;

import java.util.List;
import java.util.Map;
import java.util.Set;

class GLAccountPartUtils {
    private GLAccountPartUtils() {
    }

    static List<Long> createParts(CreateGLAccountPartTypeUseCase createGLAccountPartTypeUseCase,
                                  CreateGLAccountPartUseCase createGLAccountPartUseCase,
                                  GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort
    ) {
        GLAccountPartTypeUtils.LEVEL_TO_PART_TYPE_LANGUAGE_CODE_TO_DESCRIPTION
                .forEach((level, LanguageCodeToDescription) ->
                        createGLAccountPartTypeUseCase.create(
                                new CreateGLAccountPartTypeUseCase.CreateGLAccountPartTypeCommand(
                                        level,
                                        LanguageCodeToDescription
                                )
                        )
                );

        Long id1 = createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        null,
                        glAccountPartTypeRepositoryPort.readByLevel(1).get().getId(),
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "11",
                                        Map.of(
                                                "en", "Revenue"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id2 = createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        id1,
                        glAccountPartTypeRepositoryPort.readByLevel(2).get().getId(),
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "1",
                                        Map.of(
                                                "en", "Tax"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id3 = createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        id2,
                        glAccountPartTypeRepositoryPort.readByLevel(3).get().getId(),
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "10",
                                        Map.of(
                                                "en", "Taxes on Income"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id4 = createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        id3,
                        glAccountPartTypeRepositoryPort.readByLevel(4).get().getId(),
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "2",
                                        Map.of(
                                                "en", "Payable by individual"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id5 = createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        id4,
                        glAccountPartTypeRepositoryPort.readByLevel(5).get().getId(),
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "2",
                                        Map.of(
                                                "en", "Personal income tax"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id6 = createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        id5,
                        glAccountPartTypeRepositoryPort.readByLevel(6).get().getId(),
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
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
