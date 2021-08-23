package bhutan.eledger.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import bhutan.eledger.domain.config.balanceaccount.BalanceAccountPartStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateBalanceAccountPartTest {

    @Autowired
    private CreateBalanceAccountPartTypeUseCase createBalanceAccountPartTypeUseCase;

    @Autowired
    private CreateBalanceAccountPartUseCase createBalanceAccountPartUseCase;

    @Autowired
    private BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort;

    @Autowired
    private BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;

    @BeforeEach
    void beforeEach() {
        BalanceAccountPartTypeUtils.LEVEL_TO_PART_TYPE_LANGUAGE_CODE_TO_DESCRIPTION
                .forEach((level, LanguageCodeToDescription) ->
                        createBalanceAccountPartTypeUseCase.create(
                                new CreateBalanceAccountPartTypeUseCase.CreateBalanceAccountPartTypeCommand(
                                        level,
                                        LanguageCodeToDescription
                                )
                        )
                );

        Assertions.assertEquals(7, balanceAccountPartTypeRepositoryPort.readAll().size());

    }

    @AfterEach
    void afterEach() {
        balanceAccountPartRepositoryPort.deleteAll();
        balanceAccountPartTypeRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        var balanceAccountParts = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        null,
                        balanceAccountPartTypeRepositoryPort.readByLevel(1).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "11",
                                        Map.of(
                                                "en", "Revenue"
                                        )
                                )
                        )
                )
        );

        Assertions.assertNotNull(balanceAccountParts);
        Assertions.assertFalse(balanceAccountParts.isEmpty());
        Assertions.assertNotNull(balanceAccountParts.iterator().next().getId());
    }

    @Test
    void readTest() {
        var balanceAccountParts = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        null,
                        balanceAccountPartTypeRepositoryPort.readByLevel(1).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "11",
                                        Map.of(
                                                "en", "Revenue",
                                                "bt", "Revenue"
                                        )
                                )
                        )
                )
        );

        var partOptional =
                balanceAccountPartRepositoryPort.readById(balanceAccountParts.iterator().next().getId());

        Assertions.assertTrue(partOptional.isPresent());

        var part = partOptional.get();

        Assertions.assertEquals("11", part.getCode());
        Assertions.assertEquals(BalanceAccountPartStatus.ACTIVE, part.getStatus());
        Assertions.assertNotNull(part.getStartDate());
        Assertions.assertNull(part.getEndDate());
        Assertions.assertNotNull(part.getBalanceAccountPartLevelId());

        var description = part.getDescription();

        Assertions.assertNotNull(part.getDescription());

        var enTranslation = description.translationValue("en");
        var btTranslation = description.translationValue("bt");

        Assertions.assertTrue(enTranslation.isPresent());
        Assertions.assertTrue(btTranslation.isPresent());

        Assertions.assertEquals("Revenue", enTranslation.get());
        Assertions.assertEquals("Revenue", btTranslation.get());
    }
}
