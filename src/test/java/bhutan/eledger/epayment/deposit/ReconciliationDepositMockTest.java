package bhutan.eledger.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.GenerateReconciliationInfoUseCase;
import bhutan.eledger.application.port.in.epayment.deposit.SearchDepositUseCase;
import bhutan.eledger.application.port.in.epayment.deposit.ApproveReconciliationUseCase;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReconciliationDepositMockTest {
    @Autowired
    private GenerateReconciliationInfoUseCase generateReconciliationInfoUseCase;

    @Autowired
    private ApproveReconciliationUseCase updateDepositUseCase;

    @Autowired
    private SearchDepositUseCase searchDepositUseCase;

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {
    }

    //@Test
    void createTest() {
        var searchResult = searchDepositUseCase.search(new SearchDepositUseCase.SearchDepositCommand(
                0,
                10,
                null,
                null,
                null,
                null,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        ));

        Assertions.assertTrue(searchResult.getTotalCount() > 0);
        Assertions.assertTrue(DepositStatus.PENDING_RECONCILIATION.equals(searchResult.getContent().get(0).getStatus()));

        ApproveReconciliationUseCase.ApproveDepositReconciliationCommand setCommand =
                new ApproveReconciliationUseCase.ApproveDepositReconciliationCommand(
                        Arrays.asList(searchResult.getContent().get(0).getDepositNumber()));
        updateDepositUseCase.approveDepositReconciliation(setCommand);

        searchResult = searchDepositUseCase.search(new SearchDepositUseCase.SearchDepositCommand(
                0,
                10,
                null,
                null,
                null,
                null,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        ));
        Deposit deposit = searchResult.getContent().get(0);
        Assertions.assertTrue(DepositStatus.RECONCILED.equals(deposit.getStatus()));

        String filePathOld =
                "/resources/file/files/drc-users/00/00/00/00000000-0000-0000-0000-000000000001/2021/11/29/1638187692518/attachments/Reconciliation.xls";
        GenerateReconciliationInfoUseCase.GenerateDepositReconciliationInfoCommand command =
                new GenerateReconciliationInfoUseCase.GenerateDepositReconciliationInfoCommand(filePathOld);
        GenerateReconciliationInfoUseCase.ReconciliationInfo result =
                generateReconciliationInfoUseCase.generate(command);
        Assertions.assertTrue(null != result.getDeposits() && !result.getDeposits().isEmpty());
    }
}
