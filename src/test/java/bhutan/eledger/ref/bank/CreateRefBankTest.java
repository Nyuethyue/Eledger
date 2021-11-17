package bhutan.eledger.ref.bank;

import bhutan.eledger.application.port.in.ref.bank.CreateRefBankUseCase;
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
class CreateRefBankTest {

    @Autowired
    private CreateRefBankUseCase createRefBankUseCase;

    @Test
    void createTest() {

        Long id = createRefBankUseCase.create(
                new CreateRefBankUseCase.CreateRefBankCommand(
                        "Bank of bhutan",
                        "1235656566",
                        Map.of("en", "Test value")

                        )

        );

        Assertions.assertNotNull(id);
    }
}
