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
import java.util.Collection;
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
        CreateDepositUseCase.CreateDepositCommand createCommand =
                new CreateDepositUseCase.CreateDepositCommand(
                        1L,
                        DepositStatus.BOUNCED,
                        BigDecimal.valueOf(1221),
                        LocalDate.now().minusDays(31),
                        createReceipts(),
                        createDenominationCounts()
                );
        Long id = createDepositUseCase.create(createCommand);
        deposit = transactionTemplate.execute(status -> depositRepositoryPort.readById(id).get());
    }

    private Collection<Long> createReceipts() {
        LinkedList<Long> result = new LinkedList<>();
        result.add(1l);
        return result;
    }

    private Collection<CreateDepositUseCase.DenominationCount> createDenominationCounts() {
        var result = new LinkedList<CreateDepositUseCase.DenominationCount>();
        result.add(new CreateDepositUseCase.DenominationCount(1l, 10l));
        return result;
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
