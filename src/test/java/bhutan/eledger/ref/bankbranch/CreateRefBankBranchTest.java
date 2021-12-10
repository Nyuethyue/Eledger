package bhutan.eledger.ref.bankbranch;

import bhutan.eledger.application.port.in.ref.bank.CreateRefBankUseCase;
import bhutan.eledger.application.port.in.ref.bankbranch.CreateRefBankBranchUseCase;
import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
import bhutan.eledger.application.port.out.ref.bankbranch.RefBankBranchRepositoryPort;
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
class CreateRefBankBranchTest {

    @Autowired
    private CreateRefBankBranchUseCase createRefBankBranchUseCase;

    @Autowired
    private RefBankBranchRepositoryPort refBankBranchRepositoryPort;

    @Autowired
    private RefBankRepositoryPort refBankRepositoryPort;

    @Autowired
    private CreateRefBankUseCase createRefBankUseCase;

    @AfterEach
    void afterEach() {
        refBankBranchRepositoryPort.deleteAll();
        refBankRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {

        Long bankId = createRefBankUseCase.create(
                new CreateRefBankUseCase.CreateRefBankCommand(
                        "020202",
                        LocalDate.now().plusDays(1),
                        null,
                        Map.of("en", "Bank of Bhutan")

                )

        );
        Long id = createRefBankBranchUseCase.create(
                new CreateRefBankBranchUseCase.CreateBranchCommand(
                        "8888",
                        null,
                        "0000000",
                        LocalDate.now().plusDays(1),
                        null,
                        bankId,
                        Map.of("en", "Branch A")

                )

        );
        Assertions.assertNotNull(id);
    }

    @Test
    void readAllTest() {
        Long bankId = createRefBankUseCase.create(
                new CreateRefBankUseCase.CreateRefBankCommand(
                        "020202",
                        LocalDate.now().plusDays(1),
                        null,
                        Map.of("en", "Bank of Bhutan")

                )

        );
        Long id = createRefBankBranchUseCase.create(
                new CreateRefBankBranchUseCase.CreateBranchCommand(
                        "8888",
                        null,
                        "0000000",
                        LocalDate.now().plusDays(1),
                        null,
                        bankId,
                        Map.of("en", "Branch A")

                )

        );
        var bankBranchOptional = refBankBranchRepositoryPort.readById(id);

        var bankBranch = bankBranchOptional.get();

        Assertions.assertNotNull(bankBranch);
        Assertions.assertNotNull(bankBranch.getDescription());

        var bankBranchByBankId = refBankBranchRepositoryPort.readAllByBankId(bankId,LocalDate.now());
        Assertions.assertNotNull(bankBranchByBankId);
    }
}
