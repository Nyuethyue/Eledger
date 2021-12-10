package bhutan.eledger.ref.bankaccount;

import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartTypeUseCase;
import bhutan.eledger.application.port.in.eledger.config.glaccount.CreateGLAccountPartUseCase;
import bhutan.eledger.application.port.in.ref.bank.CreateRefBankUseCase;
import bhutan.eledger.application.port.in.ref.bankaccount.CreateRefBankAccountUseCase;
import bhutan.eledger.application.port.in.ref.bankbranch.CreateRefBankBranchUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
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
import java.util.Set;

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
    private CreateGLAccountPartUseCase createGLAccountPartUseCase;

    @Autowired
    private CreateGLAccountPartTypeUseCase createGLAccountPartTypeUseCase;

    @Autowired
    private RefBankAccountRepositoryPort refBankAccountRepositoryPort;

    @Autowired
    private RefBankBranchRepositoryPort refBankBranchRepositoryPort;

    @Autowired
    private RefBankRepositoryPort refBankRepositoryPort;

    @Autowired
    private GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Autowired
    private GLAccountPartRepositoryPort glAccountPartRepositoryPort;


    @AfterEach
    void afterEach() {
        refBankAccountRepositoryPort.deleteAll();
        glAccountPartRepositoryPort.deleteAll();
        glAccountPartTypeRepositoryPort.deleteAll();
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
        Long branchId = createRefBankBranchUseCase.create(
                new CreateRefBankBranchUseCase.CreateBranchCommand(
                        "0000",
                        "111115",
                        "0000000",
                        LocalDate.now().plusDays(1),
                        null,
                        bankId,
                        Map.of("en", "Branch A")

                )

        );
        Integer glTypeId = createGLAccountPartTypeUseCase.create(
                new CreateGLAccountPartTypeUseCase.CreateGLAccountPartTypeCommand(
                        1,
                        Map.of("en", "Major group")

                )
        );

        createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        null,
                        glTypeId,
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "11",
                                        Map.of(
                                                "en", "Revenue"
                                        )
                                )
                        )
                )
        );

        Long id = createRefBankAccountUseCase.create(
                new CreateRefBankAccountUseCase.CreateBankAccountCommand(
                        branchId,
                        "5555555",
                        LocalDate.now().plusDays(1),
                        null,
                        true,
                        Map.of("en", "Account A"),
                        new CreateRefBankAccountUseCase.BankAccountGLAccountPartCommand(
                                "11"
                        )


                )

        );
        Assertions.assertNotNull(id);
    }

    @Test
    void readTest() {
        Long bankId = createRefBankUseCase.create(
                new CreateRefBankUseCase.CreateRefBankCommand(
                        "020202",
                        LocalDate.now().plusDays(1),
                        null,
                        Map.of("en", "Bank of Bhutan")

                )

        );
        Long branchId = createRefBankBranchUseCase.create(
                new CreateRefBankBranchUseCase.CreateBranchCommand(
                        "0000",
                        "111115",
                        "0000000",
                        LocalDate.now().plusDays(1),
                        null,
                        bankId,
                        Map.of("en", "Branch A")

                )

        );
        Integer glTypeId = createGLAccountPartTypeUseCase.create(
                new CreateGLAccountPartTypeUseCase.CreateGLAccountPartTypeCommand(
                        1,
                        Map.of("en", "Major group")

                )
        );

        createGLAccountPartUseCase.create(
                new CreateGLAccountPartUseCase.CreateGLAccountPartCommand(
                        null,
                        glTypeId,
                        Set.of(
                                new CreateGLAccountPartUseCase.GLAccountPartCommand(
                                        "11",
                                        Map.of(
                                                "en", "Revenue"
                                        )
                                )
                        )
                )
        );

        Long id = createRefBankAccountUseCase.create(
                new CreateRefBankAccountUseCase.CreateBankAccountCommand(
                        branchId,
                        "5555555",
                        LocalDate.now().plusDays(1),
                        null,
                        true,
                        Map.of("en", "Account A"),
                        new CreateRefBankAccountUseCase.BankAccountGLAccountPartCommand(
                                "11"
                        )
                )

        );
        var bankAccountOptional = refBankAccountRepositoryPort.readById(id);

        Assertions.assertTrue(bankAccountOptional.isPresent());
        var bankAccount = bankAccountOptional.get();
        Assertions.assertNotNull(bankAccount);
        Assertions.assertNotNull(bankAccount.getDescription());

        var bankAccountByCode = refBankAccountRepositoryPort.readByCode(bankAccount.getCode(),LocalDate.now());
        Assertions.assertNotNull(bankAccountByCode);

        var bankAccountByBranchId = refBankAccountRepositoryPort.readAllByBranchId(branchId,LocalDate.now());
        Assertions.assertNotNull(bankAccountByBranchId);
    }

}
