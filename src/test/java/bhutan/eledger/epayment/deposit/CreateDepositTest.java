package bhutan.eledger.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.application.port.in.epayment.deposit.SearchDepositUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateDepositTest {

    @Autowired
    private CreateDepositUseCase createDepositUseCase;

    @Autowired
    private DepositRepositoryPort depositRepositoryPort;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private SearchDepositUseCase searchDepositUseCase;

    private Deposit deposit;

    @BeforeEach
    void beforeEach() {
        var receipts = new LinkedList<Long>();
        var denominationCounts = new LinkedList<CreateDepositUseCase.DenominationCount>();
        CreateDepositUseCase.CreateDepositCommand createCommand =
                new CreateDepositUseCase.CreateDepositCommand(
                        PaymentMode.CASH_WARRANT,
                        DepositStatus.BOUNCED,
                        receipts,
                        denominationCounts
                );
        Long id = createDepositUseCase.create(createCommand);
        deposit = transactionTemplate.execute(status -> depositRepositoryPort.readById(id).get());
    }

    @AfterEach
    void afterEach() {
        depositRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        var searchResult = searchDepositUseCase.search(new SearchDepositUseCase.SearchDepositCommand(
                0,
                10,
                null,
                null,
                null,
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1)
        ));

        Assertions.assertEquals(1, searchResult.getTotalCount());
    }
}
