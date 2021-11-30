package bhutan.eledger.ref.bankaccount;

import bhutan.eledger.application.port.in.ref.bank.CreateRefBankUseCase;
import bhutan.eledger.application.port.in.ref.bankaccount.CreateRefBankAccountUseCase;
import bhutan.eledger.application.port.in.ref.bankbranch.CreateRefBankBranchUseCase;
import bhutan.eledger.application.port.out.ref.bank.RefBankRepositoryPort;
import bhutan.eledger.application.port.out.ref.bankaccount.RefBankAccountRepositoryPort;
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
class CreateRefBankAccountTest {
    @Autowired
    private CreateRefBankBranchUseCase createRefBankBranchUseCase;

    @Autowired
    private CreateRefBankUseCase createRefBankUseCase;

    @Autowired
    private CreateRefBankAccountUseCase createRefBankAccountUseCase;

    @Autowired
    private RefBankAccountRepositoryPort refBankAccountRepositoryPort;

    @Autowired
    private RefBankBranchRepositoryPort refBankBranchRepositoryPort;

    @Autowired
    private RefBankRepositoryPort refBankRepositoryPort;


    @AfterEach
    void afterEach() {
        refBankAccountRepositoryPort.deleteAll();
        refBankBranchRepositoryPort.deleteAll();
        refBankRepositoryPort.deleteAll();
    }

    @Test
    void createTest() {

        Long bankId = createRefBankUseCase.create(
                new CreateRefBankUseCase.CreateRefBankCommand(
                        "4444",
                        LocalDate.now().plusDays(1),
                        Map.of("en", "Bank of Bhutan")

                )

        );
        Long branchId = createRefBankBranchUseCase.create(
                new CreateRefBankBranchUseCase.CreateBranchCommand(
                        "0000",
                        "111115",
                        "0000000",
                        LocalDate.now().plusDays(1),
                        bankId,
                        Map.of("en", "Branch A")

                )

        );
        Long id = createRefBankAccountUseCase.create(
                new CreateRefBankAccountUseCase.CreateBankAccountCommand(
                        branchId,
                        "5555555",
                        LocalDate.now().plusDays(1),
                        Map.of("en", "Account A")

                )

        );
        Assertions.assertNotNull(id);
    }

    @Test
    void readTest() {
        Long bankId = createRefBankUseCase.create(
                new CreateRefBankUseCase.CreateRefBankCommand(
                        "4444",
                        LocalDate.now().plusDays(1),
                        Map.of("en", "Bank of Bhutan")

                )

        );
        Long branchId = createRefBankBranchUseCase.create(
                new CreateRefBankBranchUseCase.CreateBranchCommand(
                        "0000",
                        "111115",
                        "0000000",
                        LocalDate.now().plusDays(1),
                        bankId,
                        Map.of("en", "Branch A")

                )

        );
        Long id = createRefBankAccountUseCase.create(
                new CreateRefBankAccountUseCase.CreateBankAccountCommand(
                        branchId,
                        "5555555",
                        LocalDate.now().plusDays(1),
                        Map.of("en", "Account A")

                )

        );

        var bankAccountOptional = refBankAccountRepositoryPort.readById(id);
        var bankAccount = bankAccountOptional.get();
        Assertions.assertNotNull(bankAccount);
        Assertions.assertNotNull(bankAccount.getDescription());

        var bankAccountByAccNumber = refBankAccountRepositoryPort.readByAccNumber(bankAccount.getAccNumber()).get();
        Assertions.assertNotNull(bankAccountByAccNumber);

        var bankAccountByBranchId = refBankAccountRepositoryPort.readAllByBranchId(branchId);
        Assertions.assertNotNull(bankAccountByBranchId);
        Assertions.assertEquals(1, bankAccountByBranchId.size());
    }

}
