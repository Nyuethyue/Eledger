package bhutan.eledger.eledger.config.glaccount;

import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.SearchGLAccountPartUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateGLAccountPartSearchTest {

    @Autowired
    private CreateGLAccountPartTypeUseCase createGLAccountPartTypeUseCase;

    @Autowired
    private CreateGLAccountPartUseCase createGLAccountPartUseCase;

    @Autowired
    private GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Autowired
    private GLAccountPartRepositoryPort glAccountPartRepositoryPort;

    @Autowired
    private SearchGLAccountPartUseCase searchGLAccountPartUseCase;

    private Integer partTypeId;

    @BeforeEach
    void beforeEach() {
        GLAccountPartTypeUtils.LEVEL_TO_PART_TYPE_LANGUAGE_CODE_TO_DESCRIPTION
                .forEach((level, LanguageCodeToDescription) ->
                        createGLAccountPartTypeUseCase.create(
                                new CreateGLAccountPartTypeUseCase.CreateGLAccountPartTypeCommand(
                                        level,
                                        LanguageCodeToDescription
                                )
                        )
                );


        partTypeId = glAccountPartTypeRepositoryPort.readByLevel(1).get().getId();

        createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        null,
                        partTypeId,
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "11",
                                        Map.of(
                                                "en", "Revenue",
                                                "dz", "DZ value"
                                        )
                                ),
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "115",
                                        Map.of(
                                                "en", "income tax",
                                                "dz", "DZ value"
                                        )
                                ),
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "2485",
                                        Map.of(
                                                "en", "Business income tax",
                                                "dz", "DZ Business income tax"
                                        )
                                )
                        )
                )
        );
    }

    @AfterEach
    void afterEach() {
        glAccountPartRepositoryPort.deleteAll();
        glAccountPartTypeRepositoryPort.deleteAll();
    }

    @Test
    void searchByCodeTest() {
        var result = searchGLAccountPartUseCase.search(new SearchGLAccountPartUseCase.SearchGLAccountPartCommand(
                        null,
                        1,
                        "code",
                        null,
                        "en",
                        "11",
                        null,
                        partTypeId
                )
        );

        Assertions.assertEquals(2, result.getTotalCount());
        Assertions.assertEquals(2, result.getTotalPages());
        Assertions.assertEquals(1, result.getContent().size());
        Assertions.assertEquals("11", result.getContent().get(0).getCode());

        result = searchGLAccountPartUseCase.search(new SearchGLAccountPartUseCase.SearchGLAccountPartCommand(
                        null,
                        null,
                        "code",
                        null,
                        "en",
                        "11",
                        null,
                        partTypeId
                )
        );

        Assertions.assertEquals(2, result.getTotalCount());
        Assertions.assertEquals(1, result.getTotalPages());
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals("11", result.getContent().get(0).getCode());
        Assertions.assertEquals("115", result.getContent().get(1).getCode());
    }

    @Test
    void searchByDescriptionTest() {
        var result = searchGLAccountPartUseCase.search(new SearchGLAccountPartUseCase.SearchGLAccountPartCommand(
                        null,
                        1,
                        "code",
                        null,
                        "en",
                        null,
                        "income",
                        partTypeId
                )
        );

        Assertions.assertEquals(2, result.getTotalCount());
        Assertions.assertEquals(2, result.getTotalPages());
        Assertions.assertEquals(1, result.getContent().size());
        Assertions.assertEquals("115", result.getContent().get(0).getCode());

        result = searchGLAccountPartUseCase.search(new SearchGLAccountPartUseCase.SearchGLAccountPartCommand(
                        null,
                        null,
                        "code",
                        null,
                        "en",
                        null,
                        "income",
                        partTypeId
                )
        );

        Assertions.assertEquals(2, result.getTotalCount());
        Assertions.assertEquals(1, result.getTotalPages());
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals("115", result.getContent().get(0).getCode());
        Assertions.assertEquals("2485", result.getContent().get(1).getCode());
    }

    @Test
    void searchByCodeNoneMatchTest() {
        var result = searchGLAccountPartUseCase.search(new SearchGLAccountPartUseCase.SearchGLAccountPartCommand(
                        null,
                        null,
                        "code",
                        null,
                        "en",
                        "113",
                        null,
                        partTypeId
                )
        );

        Assertions.assertEquals(0, result.getTotalCount());
        Assertions.assertEquals(0, result.getTotalPages());
        Assertions.assertEquals(0, result.getContent().size());
    }
}
