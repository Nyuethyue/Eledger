package bhutan.eledger.config.glaccount;

import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.domain.config.glaccount.GLAccountPartStatus;
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
        var glAccountParts = createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        null,
                        glAccountPartTypeRepositoryPort.readByLevel(1).get().getId(),
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
        Assertions.assertEquals(GLAccountPartStatus.ACTIVE, part.getStatus());
        Assertions.assertNotNull(part.getValidityPeriod().getStart());
        Assertions.assertNull(part.getValidityPeriod().getEnd());
        Assertions.assertNotNull(part.getGlAccountPartLevelId());

        var description = part.getDescription();

        Assertions.assertNotNull(part.getDescription());

        var enTranslation = description.translationValue("en");
        var btTranslation = description.translationValue("dz");

        Assertions.assertTrue(enTranslation.isPresent());
        Assertions.assertTrue(btTranslation.isPresent());

        Assertions.assertEquals("Revenue", enTranslation.get());
        Assertions.assertEquals("Revenue", btTranslation.get());
    }
}
