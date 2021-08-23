package bhutan.eledger.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountRepositoryPort;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateBalanceAccountTest {

    @Autowired
    private CreateBalanceAccountPartTypeUseCase createBalanceAccountPartTypeUseCase;

    @Autowired
    private CreateBalanceAccountPartUseCase createBalanceAccountPartUseCase;

    @Autowired
    private BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort;

    @Autowired
    private BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;

    @Autowired
    private BalanceAccountRepositoryPort balanceAccountRepositoryPort;

    @Autowired
    private CreateBalanceAccountUseCase createBalanceAccountUseCase;

    private Collection<Long> partIds;

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

        Long id1 = createBalanceAccountPartUseCase.create(
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
        ).iterator().next().getId();

        Long id2 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        id1,
                        balanceAccountPartTypeRepositoryPort.readByLevel(2).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "1",
                                        Map.of(
                                                "en", "Tax"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id3 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        id2,
                        balanceAccountPartTypeRepositoryPort.readByLevel(3).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "10",
                                        Map.of(
                                                "en", "Taxes on Income"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id4 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        id3,
                        balanceAccountPartTypeRepositoryPort.readByLevel(4).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "2",
                                        Map.of(
                                                "en", "Payable by individual"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id5 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        id4,
                        balanceAccountPartTypeRepositoryPort.readByLevel(5).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "2",
                                        Map.of(
                                                "en", "Personal income tax"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        Long id6 = createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        id5,
                        balanceAccountPartTypeRepositoryPort.readByLevel(6).get().getId(),
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "0",
                                        Map.of(
                                                "en", "Resident"
                                        )
                                )
                        )
                )
        ).iterator().next().getId();

        partIds = List.of(id3, id1, id6, id2, id5, id4);
    }

    @AfterEach
    void afterEach() {
        balanceAccountRepositoryPort.deleteAll();
        balanceAccountPartRepositoryPort.deleteAll();
        balanceAccountPartTypeRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        Long id = createBalanceAccountUseCase.create(
                new CreateBalanceAccountUseCase.CreateBalanceAccountCommand(
                        partIds,
                        new CreateBalanceAccountUseCase.BalanceAccountLastPartCommand(
                                "1002",
                                Map.of(
                                        "en", "TDS on other source of income"
                                )
                        ),
                        Map.of(
                                "en", "TDS on other source of income"
                        )
                )
        );

        Assertions.assertNotNull(id);

        var balanceAccountOptional = balanceAccountRepositoryPort.readById(id);

        Assertions.assertTrue(balanceAccountOptional.isPresent());

        var balanceAccount = balanceAccountOptional.get();

        Assertions.assertEquals("111102201002", balanceAccount.getCode());


    }
}
