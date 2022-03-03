package bhutan.eledger.epayment.payment;

import bhutan.eledger.application.port.in.eledger.accounting.CalculationNetNegativeBalanceUseCase;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculateNetBalanceTest {
    @Autowired
    private CalculationNetNegativeBalanceUseCase calculationNetNegativeBalanceUseCase;

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {
    }

    @Test
    void createTest() {
        CalculationNetNegativeBalanceUseCase.CalculateBalanceCommand commandBalance =
        new CalculationNetNegativeBalanceUseCase.CalculateBalanceCommand("OPS", LocalDate.now());
        var result = calculationNetNegativeBalanceUseCase.getNetNegativeBalance(commandBalance);
        Assertions.assertNotNull(result);
    }
}
