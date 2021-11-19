package bhutan.eledger.ref.bankbranch;

import bhutan.eledger.application.port.in.ref.bankbranch.CreateRefBankBranchUseCase;
import bhutan.eledger.application.port.out.ref.bankbranch.RefBankBranchRepositoryPort;
import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;
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
class CreateRefBankBranchTest {

    @Autowired
    private CreateRefBankBranchUseCase createRefBankBranchUseCase;

    @Autowired
    private RefBankBranchRepositoryPort refBankBranchRepositoryPort;

    @AfterEach
    void afterEach() {
        refBankBranchRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {
        Long id = createRefBankBranchUseCase.create(
                new CreateRefBankBranchUseCase.CreateBranchCommand(
                        "0002",
                        "111112",
                        "0000000",
                        1L,
                        Map.of("en", "Branch A")

                )

        );
        Assertions.assertNotNull(id);
    }

    @Test
    void readAllTest() {
        Collection<RefBankBranch> existedCurrencies = refBankBranchRepositoryPort.readAll();
        Assertions.assertNotEquals(0, existedCurrencies.size());
    }
}
