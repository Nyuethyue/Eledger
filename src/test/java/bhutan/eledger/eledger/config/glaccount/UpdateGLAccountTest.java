package bhutan.eledger.eledger.config.glaccount;

import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.UpdateGLAccountUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountRepositoryPort;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccount;
import bhutan.eledger.domain.eledger.config.glaccount.GLAccountStatus;
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
class UpdateGLAccountTest {

    @Autowired
    private CreateGLAccountPartTypeUseCase createGLAccountPartTypeUseCase;

    @Autowired
    private CreateGLAccountPartUseCase createGLAccountPartUseCase;

    @Autowired
    private GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Autowired
    private GLAccountPartRepositoryPort glAccountPartRepositoryPort;

    @Autowired
    private GLAccountRepositoryPort glAccountRepositoryPort;

    @Autowired
    private CreateGLAccountUseCase createGLAccountUseCase;

    @Autowired
    private UpdateGLAccountUseCase updateGLAccountUseCase;

    @Autowired
    private AuditManagementTestHelper auditManagementTestHelper;


    private Long glAccountId;

    @BeforeEach
    void beforeEach() {
        Collection<Long> partIds = GLAccountPartUtils.createParts(
                createGLAccountPartTypeUseCase,
                createGLAccountPartUseCase,
                glAccountPartTypeRepositoryPort
        );

        Long lastPartId = partIds.stream().max(Long::compareTo).get();


        glAccountId = createGLAccountUseCase.create(
                new CreateGLAccountUseCase.CreateGLAccountCommand(
                        partIds,
                        new CreateGLAccountUseCase.GLAccountLastPartCommand(
                                "1002",
                                lastPartId,
                                Map.of(
                                        "en", "TDS on other source of income"
                                )
                        ),
                        Map.of(
                                "en", "TDS on other source of income"
                        )
                )
        );
    }

    @AfterEach
    void afterEach() {
        glAccountRepositoryPort.deleteAll();
        glAccountPartRepositoryPort.deleteAll();
        glAccountPartTypeRepositoryPort.deleteAll();
        auditManagementTestHelper.clearAudTables();
    }

    @Test
    void updateDescriptionTest() {
        GLAccount existedGLAccount = glAccountRepositoryPort.readById(glAccountId).orElseThrow();

        updateGLAccountUseCase.updateGLAccount(glAccountId, new UpdateGLAccountUseCase.UpdateGLAccountCommand(
                Map.of(
                        "en", "Updated value",
                        "dz", "New DZ value"
                ),
                GLAccountStatus.ACTIVE,
                LocalDateTime.now().plusYears(1)
        ));

        GLAccount updatedGLAccount = glAccountRepositoryPort.readById(glAccountId).orElseThrow();

        Assertions.assertTrue(updatedGLAccount.getDescription().translationValue("dz").isPresent());
        Assertions.assertEquals("Updated value", updatedGLAccount.getDescription().translationValue("en").get());
        Assertions.assertEquals(existedGLAccount.getValidityPeriod(), updatedGLAccount.getValidityPeriod());
    }

    @Test
    void updateStatusTest() {
        GLAccount existedGLAccount = glAccountRepositoryPort.readById(glAccountId).orElseThrow();


        LocalDateTime actualDate = LocalDateTime.now().plusYears(1);

        updateGLAccountUseCase.updateGLAccount(glAccountId, new UpdateGLAccountUseCase.UpdateGLAccountCommand(
                Map.of(
                        "en", "Updated value"
                ),
                GLAccountStatus.INACTIVE,
                actualDate
        ));

        GLAccount updatedGLAccount = glAccountRepositoryPort.readById(glAccountId).orElseThrow();

        Assertions.assertEquals(existedGLAccount.getValidityPeriod().getStart(), updatedGLAccount.getValidityPeriod().getStart());
//        Assertions.assertEquals(actualDate, updatedGLAccount.getValidityPeriod().getEnd());
        Assertions.assertEquals(GLAccountStatus.INACTIVE, updatedGLAccount.getStatus());

        updateGLAccountUseCase.updateGLAccount(glAccountId, new UpdateGLAccountUseCase.UpdateGLAccountCommand(
                Map.of(
                        "en", "Updated value"
                ),
                GLAccountStatus.ACTIVE,
                actualDate.plusYears(1)
        ));

        GLAccount secondTimeUpdatedGLAccount = glAccountRepositoryPort.readById(glAccountId).orElseThrow();


//        Assertions.assertEquals(actualDate.plusYears(1), secondTimeUpdatedGLAccount.getValidityPeriod().getStart());
        Assertions.assertNull(secondTimeUpdatedGLAccount.getValidityPeriod().getEnd());
        Assertions.assertEquals(GLAccountStatus.ACTIVE, secondTimeUpdatedGLAccount.getStatus());
    }
}
