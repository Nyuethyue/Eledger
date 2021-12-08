package bhutan.eledger.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.application.port.in.epayment.deposit.SearchDepositUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreateCashPaymentUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.ref.currency.CreateRefCurrencyUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchDepositTest {
// @TODO Refactor!!!
    @Autowired
    private CreateDepositUseCase createDepositUseCase;

    @Autowired
    private CreatePaymentAdviceUseCase createPaymentAdviceUseCase;

    @Autowired
    private PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;

    @Autowired
    private CreateCashPaymentUseCase createCashPaymentUseCase;

    @Autowired
    private DepositRepositoryPort depositRepositoryPort;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private SearchDepositUseCase searchDepositUseCase;

    @Autowired
    private CreateRefCurrencyUseCase createRefCurrencyUseCase;

    @Autowired
    private RefCurrencyRepositoryPort refCurrencyRepositoryPort;

    private Deposit deposit;
    private PaymentAdvice paymentAdvice;

    @BeforeEach
    void beforeEach() {}


    @AfterEach
    void afterEach() {
    }

    @Test
    void searchTest() {
        var searchResult = searchDepositUseCase.search(new SearchDepositUseCase.SearchDepositCommand(
                0,
                10,
                null,
                null,
                null,
                null,
                LocalDate.now().minusDays(10),
                LocalDate.now().plusDays(10)
        ));

        Assertions.assertTrue(searchResult.getTotalCount() == 0);
    }
}
