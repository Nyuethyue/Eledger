package bhutan.eledger.eledger.config.glaccount;

import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartUseCase;
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
class CreateGLAccountPartTest {

    @Autowired
    private CreateGLAccountPartTypeUseCase createGLAccountPartTypeUseCase;

    @Autowired
    private CreateGLAccountPartUseCase createGLAccountPartUseCase;

    @Autowired
    private GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Autowired
    private GLAccountPartRepositoryPort glAccountPartRepositoryPort;

    @Autowired
    private AuditManagementTestHelper auditManagementTestHelper;

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

        Assertions.assertEquals(7, glAccountPartTypeRepositoryPort.readAll().size());

    }

    @AfterEach
    void afterEach() {
        glAccountPartRepositoryPort.deleteAll();
        glAccountPartTypeRepositoryPort.deleteAll();
        auditManagementTestHelper.clearAudTables();
    }

    @Test
    void createTest() {
        var glAccountParts = createGLAccountPartUseCase.create(
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
        );

        Assertions.assertNotNull(glAccountParts);
        Assertions.assertFalse(glAccountParts.isEmpty());
        Assertions.assertNotNull(glAccountParts.iterator().next().getId());
    }

    @Test
    void readTest() {
        Integer firstLevelId = glAccountPartTypeRepositoryPort.readByLevel(1).get().getId();

        var glAccountParts = createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        null,
                        firstLevelId,
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "11",
                                        Map.of(
                                                "en", "Revenue",
                                                "dz", "Revenue"
                                        )
                                )
                        )
                )
        );

        var partOptional =
                glAccountPartRepositoryPort.readById(glAccountParts.iterator().next().getId());

        Assertions.assertTrue(partOptional.isPresent());

        var part = partOptional.get();

        Assertions.assertEquals("11", part.getCode());
        Assertions.assertNotNull(part.getGlAccountPartLevelId());

        var description = part.getDescription();

        Assertions.assertNotNull(part.getDescription());

        var enTranslation = description.translationValue("en");
        var dzTranslation = description.translationValue("dz");

        Assertions.assertTrue(enTranslation.isPresent());
        Assertions.assertTrue(dzTranslation.isPresent());

        Assertions.assertEquals("Revenue", enTranslation.get());
        Assertions.assertEquals("Revenue", dzTranslation.get());

        var partsByPartId = glAccountPartRepositoryPort.readAllByPartTypeId(firstLevelId);

        Assertions.assertNotNull(partsByPartId);
        Assertions.assertEquals(1, partsByPartId.size());
        Assertions.assertEquals(part.getId(), partsByPartId.iterator().next().getId());
    }
}
