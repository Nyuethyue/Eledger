package bhutan.eledger.config.balanceaccount;

import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.CreateBalanceAccountPartUseCase;
import bhutan.eledger.application.port.in.config.balanceaccount.SearchBalanceAccountPartUseCase;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.config.balanceaccount.BalanceAccountPartTypeRepositoryPort;
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
class CreateBalanceAccountPartSearchTest {

    @Autowired
    private CreateBalanceAccountPartTypeUseCase createBalanceAccountPartTypeUseCase;

    @Autowired
    private CreateBalanceAccountPartUseCase createBalanceAccountPartUseCase;

    @Autowired
    private BalanceAccountPartTypeRepositoryPort balanceAccountPartTypeRepositoryPort;

    @Autowired
    private BalanceAccountPartRepositoryPort balanceAccountPartRepositoryPort;

    @Autowired
    private SearchBalanceAccountPartUseCase searchBalanceAccountPartUseCase;

    private Integer partTypeId;

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


        partTypeId = balanceAccountPartTypeRepositoryPort.readByLevel(1).get().getId();

        createBalanceAccountPartUseCase.create(
                new CreateBalanceAccountPartUseCase.CreateBalanceAccountPartCommand(
                        null,
                        partTypeId,
                        Set.of(
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "11",
                                        Map.of(
                                                "en", "Revenue",
                                                "dz", "DZ value"
                                        )
                                ),
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "115",
                                        Map.of(
                                                "en", "income tax",
                                                "dz", "DZ value"
                                        )
                                ),
                                new CreateBalanceAccountPartUseCase.BalanceAccountPartCommand(
                                        "2485",
                                        Map.of(
                                                "en", "Business income tax",
                                                "dz", "DZ Business income tax"
                                        )
                                )
                        )
                )
        );
    }

    @AfterEach
    void afterEach() {
        balanceAccountPartRepositoryPort.deleteAll();
        balanceAccountPartTypeRepositoryPort.deleteAll();
    }

    @Test
    void searchByCodeTest() {
        var result = searchBalanceAccountPartUseCase.search(new SearchBalanceAccountPartUseCase.SearchBalanceAccountPartCommand(
                        null,
                        1,
                        "code",
                        null,
                        "en",
                        "11",
                        null,
                        partTypeId
                )
        );

        Assertions.assertEquals(2, result.getTotalCount());
        Assertions.assertEquals(2, result.getTotalPages());
        Assertions.assertEquals(1, result.getContent().size());
        Assertions.assertEquals("11", result.getContent().get(0).getCode());

        result = searchBalanceAccountPartUseCase.search(new SearchBalanceAccountPartUseCase.SearchBalanceAccountPartCommand(
                        null,
                        null,
                        "code",
                        null,
                        "en",
                        "11",
                        null,
                        partTypeId
                )
        );

        Assertions.assertEquals(2, result.getTotalCount());
        Assertions.assertEquals(1, result.getTotalPages());
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals("11", result.getContent().get(0).getCode());
        Assertions.assertEquals("115", result.getContent().get(1).getCode());
    }

    @Test
    void searchByDescriptionTest() {
        var result = searchBalanceAccountPartUseCase.search(new SearchBalanceAccountPartUseCase.SearchBalanceAccountPartCommand(
                        null,
                        1,
                        "code",
                        null,
                        "en",
                        null,
                        "income",
                        partTypeId
                )
        );

        Assertions.assertEquals(2, result.getTotalCount());
        Assertions.assertEquals(2, result.getTotalPages());
        Assertions.assertEquals(1, result.getContent().size());
        Assertions.assertEquals("115", result.getContent().get(0).getCode());

        result = searchBalanceAccountPartUseCase.search(new SearchBalanceAccountPartUseCase.SearchBalanceAccountPartCommand(
                        null,
                        null,
                        "code",
                        null,
                        "en",
                        null,
                        "income",
                        partTypeId
                )
        );

        Assertions.assertEquals(2, result.getTotalCount());
        Assertions.assertEquals(1, result.getTotalPages());
        Assertions.assertEquals(2, result.getContent().size());
        Assertions.assertEquals("115", result.getContent().get(0).getCode());
        Assertions.assertEquals("2485", result.getContent().get(1).getCode());
    }

    @Test
    void searchByCodeNoneMatchTest() {
        var result = searchBalanceAccountPartUseCase.search(new SearchBalanceAccountPartUseCase.SearchBalanceAccountPartCommand(
                        null,
                        null,
                        "code",
                        null,
                        "en",
                        "113",
                        null,
                        partTypeId
                )
        );

        Assertions.assertEquals(0, result.getTotalCount());
        Assertions.assertEquals(0, result.getTotalPages());
        Assertions.assertEquals(0, result.getContent().size());
    }
}
