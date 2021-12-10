package bhutan.eledger.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.application.port.in.epayment.deposit.SearchDepositUseCase;
import bhutan.eledger.application.port.in.epayment.deposit.UpdateDepositUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreateCashPaymentUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.ref.currency.CreateRefCurrencyUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.application.port.out.epayment.payment.CashReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReconciliationDepositMockTest {
    @Autowired
    private CreateDepositUseCase createDepositUseCase;

    @Autowired
    private UpdateDepositUseCase updateDepositUseCase;

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

    @Autowired
    private CashReceiptRepositoryPort cashReceiptRepositoryPort;

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {
        depositRepositoryPort.deleteAll();
        cashReceiptRepositoryPort.deleteAll();
    }

    @Test
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

        UpdateDepositUseCase.SetDepositStatusesReconciledCommand setCommand =
                new UpdateDepositUseCase.SetDepositStatusesReconciledCommand(
                        Arrays.asList(searchResult.getContent().get(0).getDepositNumber()));
        updateDepositUseCase.setDepositStatusesReconciled(setCommand);

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

        Assertions.assertTrue(DepositStatus.RECONCILED.equals(searchResult.getContent().get(0).getStatus()));
    }
}
