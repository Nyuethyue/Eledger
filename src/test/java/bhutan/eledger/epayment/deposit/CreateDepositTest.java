package bhutan.eledger.epayment.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.application.port.in.epayment.deposit.SearchDepositUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreateCashPaymentUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreatePaymentCommonCommand;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.ref.currency.CreateRefCurrencyUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import bhutan.eledger.domain.epayment.payment.Receipt;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateDepositTest {
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

    private Deposit deposit;
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
                                ),
                                new CreatePaymentAdviceUseCase.PayableLineCommand(
                                        new BigDecimal("500.99"),
                                        new CreatePaymentAdviceUseCase.GLAccountCommand(
                                                "12109876543",
                                                Map.of(
                                                        "en", "Test value fine"
                                                )
                                        ),
                                        1L
                                )
                        )
                );
        Long padId = createPaymentAdviceUseCase.create(createCommand);

        paymentAdvice = transactionTemplate.execute(status -> paymentAdviceRepositoryPort.readById(padId).get());

        Long currId = createRefCurrencyUseCase.create(
                new CreateRefCurrencyUseCase.CreateCurrencyCommand(
                        "BTN",
                        "Nu.",
                        Map.of("en", "Ngultrum")

                )

        );

        var command = new CreateCashPaymentUseCase.CreateCashPaymentCommand(
                paymentAdvice.getId(),
                currId,
                Set.of(
                        new CreatePaymentCommonCommand.PaymentCommand(
                                paymentAdvice.getPayableLines()
                                        .stream()
                                        .filter(pl -> pl.getGlAccount().getCode().equals("12345678901"))
                                        .findAny().get().getId(),
                                new BigDecimal("9999.99")
                        ),
                        new CreatePaymentCommonCommand.PaymentCommand(
                                paymentAdvice.getPayableLines()
                                        .stream()
                                        .filter(pl -> pl.getGlAccount().getCode().equals("12109876543"))
                                        .findAny().get().getId(),
                                new BigDecimal("500.99")
                        )
                )
        );

        Receipt receipt = createCashPaymentUseCase.create(command);

        CreateDepositUseCase.CreateDepositCommand createDepositCommand =
                new CreateDepositUseCase.CreateDepositCommand(
                        1L,
                        DepositStatus.BOUNCED,
                        BigDecimal.valueOf(1221),
                        LocalDate.now().minusDays(31),
                        createReceipts(receipt),
                        createDenominationCounts()
                );
        Long dpId = createDepositUseCase.create(createDepositCommand);
        deposit = transactionTemplate.execute(status -> depositRepositoryPort.readById(dpId).get());
    }

    private Collection<Long> createReceipts(Receipt ...receipts) {
        LinkedList<Long> result = new LinkedList<>();
        for(Receipt r : receipts) {
            result.add(r.getId());
        }
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
