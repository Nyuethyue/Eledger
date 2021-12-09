package bhutan.eledger.ref.currency;

import bhutan.eledger.application.port.in.ref.currency.CreateRefCurrencyUseCase;
import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
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
                        "yen",
                        "￥",
                        Map.of("en", "Yen")

                )

        );
        Assertions.assertNotNull(id);
    }

    @Test
    void readTest() {
        Long id = createRefCurrencyUseCase.create(
                new CreateRefCurrencyUseCase.CreateCurrencyCommand(
                        "Yen",
                        "￥",
                        Map.of("en", "Yen")

                )

        );
        var currencyOptional = refCurrencyRepositoryPort.readById(id);
        Assertions.assertTrue(currencyOptional.isPresent());
        var currency = currencyOptional.get();
        Assertions.assertNotNull(currency);
        Assertions.assertNotNull(currency.getDescription());
    }
}
