package bhutan.eledger.config.glaccount;

import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.in.config.glaccount.history.GetGLAccountPartHistoryUseCase;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.common.history.HistoryType;
import bhutan.eledger.domain.config.glaccount.GLAccountPart;
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
class GLAccountPartHistoryTest {

    @Autowired
    private CreateGLAccountPartTypeUseCase createGLAccountPartTypeUseCase;

    @Autowired
    private CreateGLAccountPartUseCase createGLAccountPartUseCase;

    @Autowired
    private GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Autowired
    private GLAccountPartRepositoryPort glAccountPartRepositoryPort;

    @Autowired
    private GetGLAccountPartHistoryUseCase getGLAccountPartHistoryUseCase;

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

    }

    @AfterEach
    void afterEach() {
        glAccountPartRepositoryPort.deleteAll();
        glAccountPartTypeRepositoryPort.deleteAll();
        auditManagementTestHelper.clearAudTables();
    }

    @Test
    void historyTest() {
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

        GLAccountPart glAccountPart = glAccountParts.iterator().next();

        var historiesHolder = getGLAccountPartHistoryUseCase.getHistoriesById(glAccountPart.getId());

        Assertions.assertNotNull(historiesHolder);
        Assertions.assertNotNull(historiesHolder.getHistories());
        Assertions.assertFalse(historiesHolder.isEmpty());

        Assertions.assertEquals(1, historiesHolder.getHistories().size());
        var glAccountPartHistory = historiesHolder.iterator().next();

        Assertions.assertNotNull(glAccountPartHistory);
        Assertions.assertNotNull(glAccountPartHistory.getMetadata());
        Assertions.assertEquals(HistoryType.CREATED, glAccountPartHistory.getMetadata().getHistoryType());

        //todo updating history will be added after update functionality implementation
    }

}
