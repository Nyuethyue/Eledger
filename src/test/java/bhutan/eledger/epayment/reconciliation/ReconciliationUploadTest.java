package bhutan.eledger.epayment.reconciliation;

import bhutan.eledger.adapter.out.epayment.deposit.reconciliation.ReconciliationExcelLoader;
import bhutan.eledger.application.port.in.epayment.deposit.SearchReconciliationUploadHistoryUseCase;
import bhutan.eledger.application.port.in.epayment.payment.deposit.reconciliation.BankStatementImportUseCase;
import bhutan.eledger.domain.epayment.deposit.BankStatementImportReconciliationInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReconciliationUploadTest {

    @Autowired
    private BankStatementImportUseCase bankStatementImportUseCase;

    @Autowired
    private SearchReconciliationUploadHistoryUseCase searchReconciliationUploadHistoryUseCase;


    @AfterEach
    void afterEach() {
    }

    @Test
    void excelLoadFromFileTest() throws Exception {
        ReconciliationExcelLoader loader = new ReconciliationExcelLoader();

        Path resourceDirectory = Paths.get("src","test","resources", "files");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        String filePathOld = absolutePath + "/" + "Reconciliation.xls";
        List<BankStatementImportReconciliationInfo> resOld = loader.load(new FileInputStream(filePathOld), false);
        Assertions.assertTrue(resOld.size() > 0, "Empty result for old!");

        String filePathNew = absolutePath + "/" + "Reconciliation.xlsx";
        List<BankStatementImportReconciliationInfo> resNew = loader.load(new FileInputStream(filePathNew), true);
        Assertions.assertTrue(resNew.size() > 0, "Empty result for new!");
    }

    @Test
    void excelDownloadTest() {
//        String filePathOld = "/resources/file/files/drc-users/00/00/00/00000000-0000-0000-0000-000000000001/2021/11/29/1638187692518/attachments/Reconciliation.xls";
//        BankStatementImportUseCase.ImportBankStatementsCommand commandOld =
//                new BankStatementImportUseCase.ImportBankStatementsCommand(
//                         filePathOld);
//
//        List<BankStatementImportReconciliationInfo> resultOld = bankStatementImportUseCase.importStatements(commandOld);
//        Assertions.assertTrue(resultOld.size() > 0, "Empty result for excel file!");

        String filePathNew = "resources/file/files/drc-users/00/00/00/00000000-0000-0000-0000-000000000001/2021/11/29/1638187871846/attachments/Reconciliation.xlsx";
        BankStatementImportUseCase.ImportBankStatementsCommand commandNew =
                new BankStatementImportUseCase.ImportBankStatementsCommand(
                        filePathNew);

        List<BankStatementImportReconciliationInfo> resultNew = bankStatementImportUseCase.importStatements(commandNew);
        Assertions.assertTrue(resultNew.size() > 0, "Empty result for excel file!");
    }
}
