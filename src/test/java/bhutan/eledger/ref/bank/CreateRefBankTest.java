package bhutan.eledger.ref.bank;

import bhutan.eledger.application.port.in.ref.bank.CreateRefBankUseCase;
import bhutan.eledger.application.port.in.ref.bank.ReadRefBankUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
import bhutan.eledger.domain.ref.bank.RefBank;
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
class CreateRefBankTest {

    @Autowired
    private CreateRefBankUseCase createRefBankUseCase;

    @Autowired
    private RefBankRepositoryPort refBankRepositoryPort;

    @Autowired
    private ReadRefBankUseCase readRefBankUseCase;

    @AfterEach
    void afterEach() {
        refBankRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        Long id = createRefBankUseCase.create(
                new CreateRefBankUseCase.CreateRefBankCommand(
                        "66666",
                        Map.of("en", "Bank of Bhutan")

                        )

        );
        Assertions.assertNotNull(id);

        RefBank refBank = readRefBankUseCase.readById(id);
        Assertions.assertNotNull(refBank);
    }

    @Test
    void readAllTest() {
        Collection<RefBank> existedCurrencies = refBankRepositoryPort.readAll();
        Assertions.assertNotEquals(0, existedCurrencies.size());
    }
}
