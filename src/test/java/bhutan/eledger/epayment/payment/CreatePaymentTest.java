package bhutan.eledger.epayment.payment;

import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.in.eledger.config.transaction.CreateTransactionTypeAttributeUseCase;
import bhutan.eledger.application.port.in.eledger.config.transaction.CreateTransactionTypeUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreateCashMultiplePaymentsUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreatePaymentCommonCommand;
import bhutan.eledger.application.port.in.epayment.payment.SearchReceiptUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdviceUseCase;
import bhutan.eledger.application.port.in.ref.bank.CreateRefBankUseCase;
import bhutan.eledger.application.port.in.ref.bankaccount.CreateRefBankAccountUseCase;
import bhutan.eledger.application.port.in.ref.bankbranch.CreateRefBankBranchUseCase;
import bhutan.eledger.application.port.in.ref.currency.CreateRefCurrencyUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeAttributeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.transaction.TransactionRepositoryPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
import bhutan.eledger.application.port.out.ref.bankaccount.RefBankAccountRepositoryPort;
import bhutan.eledger.application.port.out.ref.bankbranch.RefBankBranchRepositoryPort;
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
import java.util.List;
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

    @Autowired
    private CreateRefBankUseCase createRefBankUseCase;

    @Autowired
    private RefBankRepositoryPort refBankRepositoryPort;

    @Autowired
    private CreateRefBankBranchUseCase createRefBankBranchUseCase;

    @Autowired
    private RefBankBranchRepositoryPort refBankBranchRepositoryPort;

    @Autowired
    private CreateGLAccountPartTypeUseCase createGLAccountPartTypeUseCase;

    @Autowired
    private GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Autowired
    private CreateGLAccountPartUseCase createGLAccountPartUseCase;

    @Autowired
    private GLAccountPartRepositoryPort glAccountPartRepositoryPort;

    @Autowired
    private CreateRefBankAccountUseCase createRefBankAccountUseCase;

    @Autowired
    private RefBankAccountRepositoryPort refBankAccountRepositoryPort;

    @Autowired
    private CreateTransactionTypeUseCase createTransactionTypeUseCase;

    @Autowired
    private CreateTransactionTypeAttributeUseCase createTransactionTypeAttributeUseCase;

    @Autowired
    private TransactionTypeRepositoryPort transactionTypeRepositoryPort;

    @Autowired
    private TransactionTypeAttributeRepositoryPort transactionTypeAttributeRepositoryPort;

    @Autowired
    private TransactionRepositoryPort transactionRepositoryPort;

    @BeforeEach
    void beforeEach() {
        Long bankId = createRefBankUseCase.create(
                new CreateRefBankUseCase.CreateRefBankCommand(
                        "020202",
                        LocalDate.now(),
                        null,
                        Map.of("en", "Bank of Bhutan")

                )

        );

        Long branchId = createRefBankBranchUseCase.create(
                new CreateRefBankBranchUseCase.CreateBranchCommand(
                        "0000",
                        "111115",
                        "0000000",
                        LocalDate.now(),
                        null,
                        bankId,
                        Map.of("en", "Branch A")

                )

        );

        Integer glTypeId = createGLAccountPartTypeUseCase.create(
                new CreateGLAccountPartTypeUseCase.CreateGLAccountPartTypeCommand(
                        1,
                        Map.of("en", "Major group")

                )
        );
        createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        null,
                        glTypeId,
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "12345678901",
                                        Map.of(
                                                "en", "Revenue"
                                        )
                                )
                        )
                )
        );

        createRefBankAccountUseCase.create(
                new CreateRefBankAccountUseCase.CreateBankAccountCommand(
                        branchId,
                        "12345678901",
                        LocalDate.now(),
                        null,
                        true,
                        Map.of("en", "Account A"),
                        new CreateRefBankAccountUseCase.BankAccountGLAccountPartCommand(
                                "12345678901"
                        )


                )

        );



        createTransactionTypeUseCase.create(
                new CreateTransactionTypeUseCase.CreateTransactionTypeCommand(
                        "PAYMENT",
                        Map.of("en", "PAYMENT")

                )

        );

        createTransactionTypeAttributeUseCase.create(
                new CreateTransactionTypeAttributeUseCase.CreateTransactionTypeAttributesCommand(
                        List.of(
                                new CreateTransactionTypeAttributeUseCase.TransactionTypeAttributeCommand(
                                        "TARGET_TRANSACTION_ID", Map.of("en", "TARGET_TRANSACTION_ID") , 1
                                ),
                                new CreateTransactionTypeAttributeUseCase.TransactionTypeAttributeCommand(
                                        "TARGET_DRN", Map.of("en", "TARGET_DRN") , 1
                                )
                        )
                )
        );

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
        transactionRepositoryPort.deleteAll();
        transactionTypeAttributeRepositoryPort.deleteAll();
        transactionTypeAttributeRepositoryPort.deleteAll();
        transactionTypeRepositoryPort.deleteAll();
        refBankAccountRepositoryPort.deleteAll();
        glAccountPartRepositoryPort.deleteAll();
        glAccountPartTypeRepositoryPort.deleteAll();
        refBankBranchRepositoryPort.deleteAll();
        refCurrencyRepositoryPort.deleteAll();
        refBankRepositoryPort.deleteAll();
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
                null,
                null,
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
                null,
                null,
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
                null,
                null,
                LocalDate.now().minusDays(1)
        ));

        Assertions.assertEquals(0, searchResult.getTotalCount());
    }
}
