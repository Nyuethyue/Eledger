package bhutan.eledger.ref.bank;

import bhutan.eledger.application.port.in.ref.bank.CreateRefBankUseCase;
import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateRefBankTest {

    @Autowired
    private CreateRefBankUseCase createRefBankUseCase;

    @Autowired
    private RefBankRepositoryPort refBankRepositoryPort;


    @AfterEach
    void afterEach() {
        refBankRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        Long id = createRefBankUseCase.create(
                new CreateRefBankUseCase.CreateRefBankCommand(
                        "66666",
                        LocalDate.now().plusDays(1),
                        Map.of("en", "Bank of Bhutan")

                )

        );
        Assertions.assertNotNull(id);
    }

    @Test
    void readTest() {
        Long id = createRefBankUseCase.create(
                new CreateRefBankUseCase.CreateRefBankCommand(
                        "9999",
                        LocalDate.now().plusDays(1),
                        Map.of("en", "Bank of Bhutan")

                )

        );

        var bankOptional = refBankRepositoryPort.readById(id);

        Assertions.assertTrue(bankOptional.isPresent());

        var bank = bankOptional.get();
        Assertions.assertNotNull(bank);
        Assertions.assertNotNull(bank.getDescription());

    }
}
