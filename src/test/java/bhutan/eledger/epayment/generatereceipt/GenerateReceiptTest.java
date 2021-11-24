package bhutan.eledger.epayment.generatereceipt;

import bhutan.eledger.application.port.in.epayment.generatereceipt.GenerateCashReceiptUseCase;
import bhutan.eledger.application.port.in.epayment.generatereceipt.GenerateReceiptCommonCommand;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.out.epayment.generatereceipt.CashReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.generatereceipt.Receipt;
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
class GenerateReceiptTest {

    @Autowired
    private CreatePaymentAdviceUseCase createPaymentAdviceUseCase;

    @Autowired
    private PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;

    @Autowired
    private GenerateCashReceiptUseCase generateCashReceiptUseCase;

    @Autowired
    private CashReceiptRepositoryPort cashReceiptRepositoryPort;

    @Autowired
    private TransactionTemplate transactionTemplate;

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

        var command = new GenerateCashReceiptUseCase.GenerateCashReceiptCommand(
                paymentAdvice.getId(),
                "USD",
                Set.of(
                        new GenerateReceiptCommonCommand.PaymentCommand(
                                paymentAdvice.getPayableLines().stream().findAny().get().getId(),
                                new BigDecimal("9999.99")
                        )
                )
        );

        Receipt receipt = generateCashReceiptUseCase.generate(command);

        Assertions.assertNotNull(receipt);
    }
}
