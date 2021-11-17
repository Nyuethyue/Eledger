package bhutan.eledger.ref.paymentmode;

import bhutan.eledger.application.port.out.ref.paymentmode.PaymentModeRepositoryPort;
import bhutan.eledger.domain.ref.paymentmode.PaymentMode;
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
class ReadPaymentModeTest {
    @Autowired
    private PaymentModeRepositoryPort paymentModeRepositoryPort;

    @Test
    void readAllTest(){
        Collection<PaymentMode> existedPaymentModes = paymentModeRepositoryPort.readAll();
        Assertions.assertEquals(6, existedPaymentModes.size());
    }
}
