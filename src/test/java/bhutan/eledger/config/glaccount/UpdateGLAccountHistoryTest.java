package bhutan.eledger.config.glaccount;

import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.in.config.glaccount.CreateGLAccountUseCase;
import bhutan.eledger.application.port.in.config.glaccount.UpdateGLAccountUseCase;
import bhutan.eledger.application.port.in.config.glaccount.history.GetGLAccountHistoryUseCase;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.config.glaccount.GLAccountRepositoryPort;
import bhutan.eledger.common.history.HistoryType;
import bhutan.eledger.domain.config.glaccount.GLAccount;
import bhutan.eledger.domain.config.glaccount.GLAccountStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateGLAccountHistoryTest {

    @Autowired
    private CreateGLAccountPartTypeUseCase createGLAccountPartTypeUseCase;

    @Autowired
    private CreateGLAccountPartUseCase createGLAccountPartUseCase;

    @Autowired
    private GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Autowired
    private GLAccountPartRepositoryPort glAccountPartRepositoryPort;

    @Autowired
    private GLAccountRepositoryPort GLAccountRepositoryPort;

    @Autowired
    private CreateGLAccountUseCase createGLAccountUseCase;

    @Autowired
    private UpdateGLAccountUseCase updateGLAccountUseCase;

    @Autowired
    private GetGLAccountHistoryUseCase getGLAccountHistoryUseCase;

    @Autowired
    private AuditManagementTestHelper auditManagementTestHelper;


    private Collection<Long> partIds;

    @BeforeEach
    void beforeEach() {
        partIds = GLAccountPartUtils.createParts(
                createGLAccountPartTypeUseCase,
                createGLAccountPartUseCase,
                glAccountPartTypeRepositoryPort
        );
    }

    @AfterEach
    void afterEach() {
        GLAccountRepositoryPort.deleteAll();
        glAccountPartRepositoryPort.deleteAll();
        glAccountPartTypeRepositoryPort.deleteAll();
        auditManagementTestHelper.clearAudTables();
    }

    @Test
    void updateStatusTest() {
        var glAccountId = createGLAccountUseCase.create(
                new CreateGLAccountUseCase.CreateGLAccountCommand(
                        partIds,
                        new CreateGLAccountUseCase.GLAccountLastPartCommand(
                                "1002",
                                Map.of(
                                        "en", "TDS on other source of income"
                                )
                        ),
                        Map.of(
                                "en", "TDS on other source of income"
                        )
                )
        );

        var historiesHolder = getGLAccountHistoryUseCase.getHistoriesById(glAccountId);

        Assertions.assertNotNull(historiesHolder);
        Assertions.assertNotNull(historiesHolder.getHistories());
        Assertions.assertFalse(historiesHolder.isEmpty());

        Assertions.assertEquals(1, historiesHolder.getHistories().size());
        var glAccountHistory = historiesHolder.iterator().next();

        Assertions.assertNotNull(glAccountHistory);
        Assertions.assertNotNull(glAccountHistory.getMetadata());
        Assertions.assertEquals(HistoryType.CREATED, glAccountHistory.getMetadata().getHistoryType());


        GLAccount existedGLAccount = GLAccountRepositoryPort.readById(glAccountId).orElseThrow();

        LocalDateTime actualDate = LocalDateTime.now().plusYears(1);

        updateGLAccountUseCase.updateGLAccount(glAccountId, new UpdateGLAccountUseCase.UpdateGLAccountCommand(
                Map.of(
                        "en", "Updated value"
                ),
                GLAccountStatus.INACTIVE,
                actualDate
        ));

        GLAccount updatedGLAccount = GLAccountRepositoryPort.readById(glAccountId).orElseThrow();


        var historiesAfterUpdate = getGLAccountHistoryUseCase.getHistoriesById(glAccountId);

        Assertions.assertEquals(2, historiesAfterUpdate.getHistories().size());

        Assertions.assertEquals(existedGLAccount.getStatus(), historiesAfterUpdate.getHistories().get(0).getDto().getStatus());
        Assertions.assertEquals(updatedGLAccount.getStatus(), historiesAfterUpdate.getHistories().get(1).getDto().getStatus());
        Assertions.assertEquals(existedGLAccount.getDescription().translationValue("en"), historiesAfterUpdate.getHistories().get(0).getDto().getDescription().translationValue("en"));
        Assertions.assertEquals(updatedGLAccount.getDescription().translationValue("en"), historiesAfterUpdate.getHistories().get(1).getDto().getDescription().translationValue("en"));
    }
}
