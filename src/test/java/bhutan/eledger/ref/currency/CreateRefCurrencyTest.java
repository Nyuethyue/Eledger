package bhutan.eledger.ref.currency;

import bhutan.eledger.application.port.in.ref.currency.CreateRefCurrencyUseCase;
import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.util.Collection;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateRefCurrencyTest {

    @Autowired
    private CreateRefCurrencyUseCase createRefCurrencyUseCase;

    @Autowired
    private RefCurrencyRepositoryPort refCurrencyRepositoryPort;

    @AfterEach
    void afterEach() {
        refCurrencyRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        Long id = createRefCurrencyUseCase.create(
                new CreateRefCurrencyUseCase.CreateCurrencyCommand(
                        "DOL",
                        "Nu.",
                        Map.of("en", "BTN")

                )

        );
        Assertions.assertNotNull(id);
    }

    @Test
    void readAllTest() {
        Collection<RefCurrency> existedCurrencies = refCurrencyRepositoryPort.readAll();
        Assertions.assertNotNull(existedCurrencies);
    }
}
