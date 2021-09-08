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
import java.util.Map;

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

        partIds = BalanceAccountPartUtils.createParts(
                createBalanceAccountPartTypeUseCase,
                createBalanceAccountPartUseCase,
                balanceAccountPartTypeRepositoryPort
        );
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
