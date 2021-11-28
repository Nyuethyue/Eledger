package bhutan.eledger.eledger.config.transaction;

import bhutan.eledger.application.port.in.eledger.config.transaction.CreateTransactionTypeUseCase;
import bhutan.eledger.application.port.out.eledger.config.transaction.TransactionTypeRepositoryPort;
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
class CreateTransactionTypeTest {

    @Autowired
    private CreateTransactionTypeUseCase createTransactionTypeUseCase;

    @Autowired
    private TransactionTypeRepositoryPort transactionTypeRepositoryPort;

    private final String transactionTypeCode = "LIABD";

    private final Map<String, String> descriptionMap = Map.of(
            "en", "Liability en",
            "dz", "Liability dz"
    );

    @AfterEach
    void afterEach() {
        transactionTypeRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {

        Long id = createTransactionTypeUseCase.create(
                new CreateTransactionTypeUseCase.CreateTransactionTypeCommand(
                        transactionTypeCode,
                        descriptionMap
                )
        );

        Assertions.assertNotNull(id);
    }

    @Test
    void readTest() {

        Long id = createTransactionTypeUseCase.create(
                new CreateTransactionTypeUseCase.CreateTransactionTypeCommand(
                        transactionTypeCode,
                        descriptionMap
                )
        );

        var transactionTypeOptional = transactionTypeRepositoryPort.readById(id);

        Assertions.assertTrue(transactionTypeOptional.isPresent());

        var partType = transactionTypeOptional.get();

        Assertions.assertEquals(transactionTypeCode, partType.getCode());

        var description = partType.getDescription();

        Assertions.assertNotNull(partType.getDescription());

        var enTranslation = description.translationValue("en");
        var dzTranslation = description.translationValue("dz");

        Assertions.assertTrue(enTranslation.isPresent());
        Assertions.assertTrue(dzTranslation.isPresent());

        Assertions.assertEquals(descriptionMap.get("en"), enTranslation.get());
        Assertions.assertEquals(descriptionMap.get("dz"), dzTranslation.get());
    }
}
