package bhutan.eledger.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreateCashPaymentUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreatePaymentCommonCommand;
import bhutan.eledger.application.port.in.epayment.payment.SearchReceiptUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.out.epayment.payment.CashReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.payment.Receipt;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreatePaymentTest {

    @Autowired
    private CreatePaymentAdviceUseCase createPaymentAdviceUseCase;

    @Autowired
    private PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;

    @Autowired
    private CreateCashPaymentUseCase createCashPaymentUseCase;

    @Autowired
    private CashReceiptRepositoryPort cashReceiptRepositoryPort;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private SearchReceiptUseCase searchReceiptUseCase;

    private PaymentAdvice paymentAdvice;

    @BeforeEach
    void beforeEach() {
        CreatePaymentAdviceUseCase.CreatePaymentAdviceCommand createCommand =
                new CreatePaymentAdviceUseCase.CreatePaymentAdviceCommand(
                        "TestDrn",
                        new CreatePaymentAdviceUseCase.TaxpayerCommand(
                                "TestTpn",
                                "TaxPayerName"
                        ),
                        LocalDate.now().plusMonths(1),
                        new CreatePaymentAdviceUseCase.PeriodCommand(
                                "2021",
                                "M04"
                        ),
                        Set.of(
                                new CreatePaymentAdviceUseCase.PayableLineCommand(
                                        new BigDecimal("9999.99"),
                                        new CreatePaymentAdviceUseCase.GLAccountCommand(
                                                "12345678901",
                                                Map.of(
                                                        "en", "Test value"
                                                )
                                        ),
                                        1L
                                )
                        )
                );
        Long id = createPaymentAdviceUseCase.create(createCommand);
        paymentAdvice = transactionTemplate.execute(status -> paymentAdviceRepositoryPort.readById(id).get());
    }

    @AfterEach
    void afterEach() {
        cashReceiptRepositoryPort.deleteAll();
        paymentAdviceRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {

        var command = new CreateCashPaymentUseCase.CreateCashPaymentCommand(
                paymentAdvice.getId(),
                "USD",
                Set.of(
                        new CreatePaymentCommonCommand.PaymentCommand(
                                paymentAdvice.getPayableLines().stream().findAny().get().getId(),
                                new BigDecimal("9999.99")
                        )
                )
        );

        Receipt receipt = createCashPaymentUseCase.create(command);

        Assertions.assertNotNull(receipt);
        Assertions.assertNotNull(receipt.getReceiptNumber());

        var searchResult = searchReceiptUseCase.search(new SearchReceiptUseCase.SearchReceiptCommand(
                0,
                10,
                null,
                null,
                null,
                null,
                null,
                null
        ));

        Assertions.assertEquals(1, searchResult.getTotalCount());
    }
}
