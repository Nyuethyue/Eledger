package bhutan.eledger.ref.currency;

import bhutan.eledger.application.port.out.ref.currency.RefCurrencyRepositoryPort;
import bhutan.eledger.domain.ref.currency.RefCurrency;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReadRefCurrencyTest {
    @Autowired
    private RefCurrencyRepositoryPort refCurrencyRepositoryPort;

    @Test
    void readAllTest() {
        Collection<RefCurrency> existedCurrencies = refCurrencyRepositoryPort.readAll();
        Assertions.assertEquals(3, existedCurrencies.size());
    }
}
