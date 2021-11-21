package bhutan.eledger.eledger.config.glaccount;

import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountRepositoryPort;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateGLAccountTest {

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
    private AuditManagementTestHelper auditManagementTestHelper;

    private Collection<Long> partIds;
    private Long lastPartId;

    @BeforeEach
    void beforeEach() {

        partIds = GLAccountPartUtils.createParts(
                createGLAccountPartTypeUseCase,
                createGLAccountPartUseCase,
                glAccountPartTypeRepositoryPort
        );

        lastPartId = partIds.stream().max(Long::compareTo).get();
    }

    @AfterEach
    void afterEach() {
        glAccountRepositoryPort.deleteAll();
        glAccountPartRepositoryPort.deleteAll();
        glAccountPartTypeRepositoryPort.deleteAll();
        auditManagementTestHelper.clearAudTables();
    }

    @Test
    void createTest() {
        Long id = createGLAccountUseCase.create(
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

        Assertions.assertNotNull(id);

        var glAccountOptional = glAccountRepositoryPort.readById(id);

        Assertions.assertTrue(glAccountOptional.isPresent());

        var glAccount = glAccountOptional.get();

        Assertions.assertEquals("111102201002", glAccount.getCode());
    }
}
