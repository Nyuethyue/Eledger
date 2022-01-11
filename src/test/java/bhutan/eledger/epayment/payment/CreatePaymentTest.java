package bhutan.eledger.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreateCashMultiplePaymentsUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreatePaymentCommonCommand;
import bhutan.eledger.application.port.in.epayment.payment.SearchReceiptUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdviceUseCase;
import bhutan.eledger.application.port.in.ref.currency.CreateRefCurrencyUseCase;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import bhutan.eledger.domain.epayment.payment.Receipt;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
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
    private CreateCashMultiplePaymentsUseCase createCashMultiplePaymentsUseCase;

    @Autowired
    private ReceiptRepositoryPort receiptRepositoryPort;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private SearchReceiptUseCase searchReceiptUseCase;

    //todo remove ref dependencies
    @Autowired
    private CreateRefCurrencyUseCase createRefCurrencyUseCase;
    @Autowired
    private RefCurrencyRepositoryPort refCurrencyRepositoryPort;

    private PaymentAdvice paymentAdvice;

    @BeforeEach
    void beforeEach() {
        UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand createCommand =
                new UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand(
                        "TestDrn",
                        new UpsertPaymentAdviceUseCase.TaxpayerCommand(
                                "TestTpn",
                                "TaxPayerName"
                        ),
                        LocalDate.now().plusMonths(1),
                        new UpsertPaymentAdviceUseCase.PeriodCommand(
                                "2021",
                                "M04"
                        ),
                        Set.of(
                                new UpsertPaymentAdviceUseCase.PayableLineCommand(
                                        new BigDecimal("9999.99"),
                                        new UpsertPaymentAdviceUseCase.GLAccountCommand(
                                                "12345678901",
                                                Map.of(
                                                        "en", "Test value"
                                                )
                                        ),
                                        1L
                                ),
                                new UpsertPaymentAdviceUseCase.PayableLineCommand(
                                        new BigDecimal("500.99"),
                                        new UpsertPaymentAdviceUseCase.GLAccountCommand(
                                                "12109876543",
                                                Map.of(
                                                        "en", "Test value fine"
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
        receiptRepositoryPort.deleteAll();
        paymentAdviceRepositoryPort.deleteAll();
        refCurrencyRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        Long currId;
        if (refCurrencyRepositoryPort.existsByCode("BTN")) {
            currId = refCurrencyRepositoryPort.readByCode("BTN").get().getId();
        } else {
            currId = createRefCurrencyUseCase.create(
                    new CreateRefCurrencyUseCase.CreateCurrencyCommand(
                            "BTN",
                            "Nu.",
                            Map.of("en", "Ngultrum")

                    )

            );
        }

        var command = new CreateCashMultiplePaymentsUseCase.CreateCashMultiplePaymentsCommand(
                currId,
                Set.of(
                        new CreateCashMultiplePaymentsUseCase.CreateCashPaymentCommand(
                                paymentAdvice.getId(),
                                Set.of(
                                        new CreatePaymentCommonCommand.PayableLineCommand(
                                                paymentAdvice.getPayableLines()
                                                        .stream()
                                                        .filter(pl -> pl.getGlAccount().getCode().equals("12345678901"))
                                                        .findAny().get().getId(),
                                                new BigDecimal("9999.99")
                                        ),
                                        new CreatePaymentCommonCommand.PayableLineCommand(
                                                paymentAdvice.getPayableLines()
                                                        .stream()
                                                        .filter(pl -> pl.getGlAccount().getCode().equals("12109876543"))
                                                        .findAny().get().getId(),
                                                new BigDecimal("500.99")
                                        )
                                )
                        )
                )

        );

        Receipt receipt = createCashMultiplePaymentsUseCase.create(command);

        Assertions.assertNotNull(receipt);
        Assertions.assertNotNull(receipt.getReceiptNumber());
        Assertions.assertEquals(ReceiptStatus.PAID, receipt.getStatus());

        var searchResult = searchReceiptUseCase.search(new SearchReceiptUseCase.SearchReceiptCommand(
                0,
                10,
                null,
                null,
                null,
                null,
                null,
                "12",
                LocalDate.now()
        ));

        Assertions.assertEquals(1, searchResult.getTotalCount());

        searchResult = searchReceiptUseCase.search(new SearchReceiptUseCase.SearchReceiptCommand(
                0,
                10,
                null,
                null,
                null,
                null,
                null,
                "12",
                null
        ));

        Assertions.assertEquals(1, searchResult.getTotalCount());

        searchResult = searchReceiptUseCase.search(new SearchReceiptUseCase.SearchReceiptCommand(
                0,
                10,
                null,
                null,
                null,
                null,
                null,
                "12",
                LocalDate.now().minusDays(1)
        ));

        Assertions.assertEquals(0, searchResult.getTotalCount());
    }
}
