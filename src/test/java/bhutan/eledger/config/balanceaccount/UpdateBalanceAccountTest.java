package bhutan.eledger.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.UpdateBalanceAccountUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccount;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountStatus;
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
class UpdateBalanceAccountTest {

    @Autowired
    private CreateBalanceAccountPartTypeUseCase createBalanceAccountPartTypeUseCase;

    @Autowired
    private CreateBalanceAccountPartUseCase createBalanceAccountPartUseCase;

    @Autowired
    private BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort;

    @Autowired
    private BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;

    @Autowired
    private BalanceAccountRepositoryPort balanceAccountRepositoryPort;

    @Autowired
    private CreateBalanceAccountUseCase createBalanceAccountUseCase;

    @Autowired
    private UpdateBalanceAccountUseCase updateBalanceAccountUseCase;

    @Autowired
    private AuditManagementTestHelper auditManagementTestHelper;


    private Long balanceAccountId;

    @BeforeEach
    void beforeEach() {
        Collection<Long> partIds = BalanceAccountPartUtils.createParts(
                createBalanceAccountPartTypeUseCase,
                createBalanceAccountPartUseCase,
                balanceAccountPartTypeRepositoryPort
        );


        balanceAccountId = createBalanceAccountUseCase.create(
                new CreateBalanceAccountUseCase.CreateBalanceAccountCommand(
                        partIds,
                        new CreateBalanceAccountUseCase.BalanceAccountLastPartCommand(
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
    }

    @AfterEach
    void afterEach() {
        balanceAccountRepositoryPort.deleteAll();
        balanceAccountPartRepositoryPort.deleteAll();
        balanceAccountPartTypeRepositoryPort.deleteAll();
        auditManagementTestHelper.clearAudTables();
    }

    @Test
    void updateDescriptionTest() {
        BalanceAccount existedBalanceAccount = balanceAccountRepositoryPort.readById(balanceAccountId).orElseThrow();

        updateBalanceAccountUseCase.updateBalanceAccount(balanceAccountId, new UpdateBalanceAccountUseCase.UpdateBalanceAccountCommand(
                Map.of(
                        "en", "Updated value",
                        "dz", "New DZ value"
                ),
                BalanceAccountStatus.ACTIVE,
                LocalDateTime.now().plusYears(1)
        ));

        BalanceAccount updatedBalanceAccount = balanceAccountRepositoryPort.readById(balanceAccountId).orElseThrow();

        Assertions.assertTrue(updatedBalanceAccount.getDescription().translationValue("dz").isPresent());
        Assertions.assertEquals("Updated value", updatedBalanceAccount.getDescription().translationValue("en").get());
        Assertions.assertEquals(existedBalanceAccount.getValidityPeriod(), updatedBalanceAccount.getValidityPeriod());
    }

    @Test
    void updateStatusTest() {
        BalanceAccount existedBalanceAccount = balanceAccountRepositoryPort.readById(balanceAccountId).orElseThrow();


        LocalDateTime actualDate = LocalDateTime.now().plusYears(1);

        updateBalanceAccountUseCase.updateBalanceAccount(balanceAccountId, new UpdateBalanceAccountUseCase.UpdateBalanceAccountCommand(
                Map.of(
                        "en", "Updated value"
                ),
                BalanceAccountStatus.INACTIVE,
                actualDate
        ));

        BalanceAccount updatedBalanceAccount = balanceAccountRepositoryPort.readById(balanceAccountId).orElseThrow();

        Assertions.assertEquals(existedBalanceAccount.getValidityPeriod().getStart(), updatedBalanceAccount.getValidityPeriod().getStart());
        Assertions.assertEquals(actualDate, updatedBalanceAccount.getValidityPeriod().getEnd());
        Assertions.assertEquals(BalanceAccountStatus.INACTIVE, updatedBalanceAccount.getStatus());

        updateBalanceAccountUseCase.updateBalanceAccount(balanceAccountId, new UpdateBalanceAccountUseCase.UpdateBalanceAccountCommand(
                Map.of(
                        "en", "Updated value"
                ),
                BalanceAccountStatus.ACTIVE,
                actualDate.plusYears(1)
        ));

        BalanceAccount secondTimeUpdatedBalanceAccount = balanceAccountRepositoryPort.readById(balanceAccountId).orElseThrow();


        Assertions.assertEquals(actualDate.plusYears(1), secondTimeUpdatedBalanceAccount.getValidityPeriod().getStart());
        Assertions.assertNull(secondTimeUpdatedBalanceAccount.getValidityPeriod().getEnd());
        Assertions.assertEquals(BalanceAccountStatus.ACTIVE, secondTimeUpdatedBalanceAccount.getStatus());
    }
}
