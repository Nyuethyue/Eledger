package bhutan.eledger.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.UpdateBalanceAccountUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.history.GetBalanceAccountHistoryUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountRepositoryPort;
import bhutan.eledger.common.history.HistoryType;
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
class UpdateBalanceAccountHistoryTest {

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
    private GetBalanceAccountHistoryUseCase getBalanceAccountHistoryUseCase;

    @Autowired
    private AuditManagementTestHelper auditManagementTestHelper;


    private Collection<Long> partIds;

    @BeforeEach
    void beforeEach() {
        partIds = BalanceAccountPartUtils.createParts(
                createBalanceAccountPartTypeUseCase,
                createBalanceAccountPartUseCase,
                balanceAccountPartTypeRepositoryPort
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
    void updateStatusTest() {
        var balanceAccountId = createBalanceAccountUseCase.create(
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

        var historiesHolder = getBalanceAccountHistoryUseCase.getHistoriesById(balanceAccountId);

        Assertions.assertNotNull(historiesHolder);
        Assertions.assertNotNull(historiesHolder.getHistories());
        Assertions.assertFalse(historiesHolder.isEmpty());

        Assertions.assertEquals(1, historiesHolder.getHistories().size());
        var balanceAccountHistory = historiesHolder.iterator().next();

        Assertions.assertNotNull(balanceAccountHistory);
        Assertions.assertNotNull(balanceAccountHistory.getMetadata());
        Assertions.assertEquals(HistoryType.CREATED, balanceAccountHistory.getMetadata().getHistoryType());


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


        var historiesAfterUpdate = getBalanceAccountHistoryUseCase.getHistoriesById(balanceAccountId);

        Assertions.assertEquals(2, historiesAfterUpdate.getHistories().size());

        Assertions.assertEquals(existedBalanceAccount.getStatus(), historiesAfterUpdate.getHistories().get(0).getDto().getStatus());
        Assertions.assertEquals(updatedBalanceAccount.getStatus(), historiesAfterUpdate.getHistories().get(1).getDto().getStatus());
        Assertions.assertEquals(existedBalanceAccount.getDescription().translationValue("en"), historiesAfterUpdate.getHistories().get(0).getDto().getDescription().translationValue("en"));
        Assertions.assertEquals(updatedBalanceAccount.getDescription().translationValue("en"), historiesAfterUpdate.getHistories().get(1).getDto().getDescription().translationValue("en"));
    }
}
