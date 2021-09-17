package bhutan.eledger.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.history.GetBalanceAccountPartHistoryUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import bhutan.eledger.common.history.HistoryType;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPart;
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
class BalanceAccountPartHistoryTest {

    @Autowired
    private CreateBalanceAccountPartTypeUseCase createBalanceAccountPartTypeUseCase;

    @Autowired
    private CreateBalanceAccountPartUseCase createBalanceAccountPartUseCase;

    @Autowired
    private BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort;

    @Autowired
    private BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;

    @Autowired
    private GetBalanceAccountPartHistoryUseCase getBalanceAccountPartHistoryUseCase;

    @Autowired
    private AuditManagementTestHelper auditManagementTestHelper;

    @BeforeEach
    void beforeEach() {
        BalanceAccountPartTypeUtils.LEVEL_TO_PART_TYPE_LANGUAGE_CODE_TO_DESCRIPTION
                .forEach((level, LanguageCodeToDescription) ->
                        createBalanceAccountPartTypeUseCase.create(
                                new CreateBalanceAccountPartTypeUseCase.CreateBalanceAccountPartTypeCommand(
                                        level,
                                        LanguageCodeToDescription
                                )
                        )
                );

    }

    @AfterEach
    void afterEach() {
        balanceAccountPartRepositoryPort.deleteAll();
        balanceAccountPartTypeRepositoryPort.deleteAll();
        auditManagementTestHelper.clearAudTables();
    }

    @Test
    void historyTest() {
        var balanceAccountParts = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        null,
                        balanceAccountPartTypeRepositoryPort.readByLevel(1).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "11",
                                        Map.of(
                                                "en", "Revenue"
                                        )
                                )
                        )
                )
        );

        BalanceAccountPart balanceAccountPart = balanceAccountParts.iterator().next();

        var historiesHolder = getBalanceAccountPartHistoryUseCase.getHistoriesById(balanceAccountPart.getId());

        Assertions.assertNotNull(historiesHolder);
        Assertions.assertNotNull(historiesHolder.getHistories());
        Assertions.assertFalse(historiesHolder.isEmpty());

        Assertions.assertEquals(1, historiesHolder.getHistories().size());
        var balanceAccountPartHistory = historiesHolder.iterator().next();

        Assertions.assertNotNull(balanceAccountPartHistory);
        Assertions.assertNotNull(balanceAccountPartHistory.getMetadata());
        Assertions.assertEquals(HistoryType.CREATED, balanceAccountPartHistory.getMetadata().getHistoryType());

        //todo updating history will be added after update functionality implementation
    }

}
