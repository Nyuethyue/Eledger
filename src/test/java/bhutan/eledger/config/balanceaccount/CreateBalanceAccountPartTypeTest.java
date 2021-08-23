package bhutan.eledger.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateBalanceAccountPartTypeTest {

    @Autowired
    private CreateBalanceAccountPartTypeUseCase createBalanceAccountPartTypeUseCase;

    @Autowired
    private BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort;

    @AfterEach
    void afterEach() {
        balanceAccountPartTypeRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {

        Integer id = createBalanceAccountPartTypeUseCase.create(
                new CreateBalanceAccountPartTypeUseCase.CreateBalanceAccountPartTypeCommand(
                        1,
                        BalanceAccountPartTypeUtils.partTypeLanguageCodeToDescriptionByLevel(1)
                )
        );

        Assertions.assertNotNull(id);
    }

    @Test
    void readTest() {
        var languageCodeToDescription = BalanceAccountPartTypeUtils.partTypeLanguageCodeToDescriptionByLevel(1);

        Integer id = createBalanceAccountPartTypeUseCase.create(
                new CreateBalanceAccountPartTypeUseCase.CreateBalanceAccountPartTypeCommand(
                        1,
                        languageCodeToDescription
                )
        );

        var partTypeOptional = balanceAccountPartTypeRepositoryPort.readById(id);

        Assertions.assertTrue(partTypeOptional.isPresent());

        var partType = partTypeOptional.get();

        Assertions.assertEquals(1, partType.getLevel());

        var description = partType.getDescription();

        Assertions.assertNotNull(partType.getDescription());

        var enTranslation = description.translationValue("en");
        var btTranslation = description.translationValue("bt");

        Assertions.assertTrue(enTranslation.isPresent());
        Assertions.assertTrue(btTranslation.isPresent());

        Assertions.assertEquals(languageCodeToDescription.get("en"), enTranslation.get());
        Assertions.assertEquals(languageCodeToDescription.get("bt"), btTranslation.get());
    }
}
