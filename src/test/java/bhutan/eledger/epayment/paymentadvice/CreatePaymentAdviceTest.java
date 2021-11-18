package bhutan.eledger.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdviceUseCase;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreatePaymentAdviceTest {

    @Autowired
    private CreatePaymentAdviceUseCase createPaymentAdviceUseCase;

    @Autowired
    private SearchPaymentAdviceUseCase searchPaymentAdviceUseCase;

    @Autowired
    private PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;

    @AfterEach
    void afterEach() {
        paymentAdviceRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        CreatePaymentAdviceUseCase.CreatePaymentAdviceCommand createCommand =
                new CreatePaymentAdviceUseCase.CreatePaymentAdviceCommand(
                        "TestDrn",
                        new CreatePaymentAdviceUseCase.TaxpayerCommand(
                                "TAB12345",
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
                                        )
                                )
                        )
                );
        Long id = createPaymentAdviceUseCase.create(createCommand);

        Assertions.assertNotNull(id);

        SearchPaymentAdviceUseCase.SearchPaymentAdviseCommand command =
                new SearchPaymentAdviceUseCase.SearchPaymentAdviseCommand(0, 10, "taxpayer.tpn", null, "PIT", createCommand.getTaxpayer().getTpn(), null);
        SearchResult<PaymentAdvice> searchResult = searchPaymentAdviceUseCase.search(command);
        Assertions.assertNotNull(searchResult);
        List<PaymentAdvice> content = searchResult.getContent();
        Assertions.assertNotNull(content);
        Assertions.assertEquals(1, content.size());
        Assertions.assertEquals(content.get(0).getDrn(), createCommand.getDrn());
    }
}
