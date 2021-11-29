package bhutan.eledger.epayment.payment;

import bhutan.eledger.application.port.in.epayment.reconciliation.BankStatementImportUseCase;
import bhutan.eledger.adapter.out.fms.epayment.reconciliation.ReconciliationExcelLoader;
import bhutan.eledger.domain.epayment.BankStatementImportReconciliationInfo;
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

    @AfterEach
    void afterEach() {
    }

    @Test
    void excelLoadFromFileTest() throws Exception {
        ReconciliationExcelLoader loader = new ReconciliationExcelLoader();

        Path resourceDirectory = Paths.get("src","test","resources", "files");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();

        List<BankStatementImportReconciliationInfo> resOld = loader.load(new FileInputStream( absolutePath + "\\" + "Reconciliation.xls"), false);
        Assertions.assertTrue(resOld.size() > 0, "Empty result for old!");
        List<BankStatementImportReconciliationInfo> resNew = loader.load(new FileInputStream( absolutePath + "\\" + "Reconciliation.xlsx"), true);
        Assertions.assertTrue(resNew.size() > 0, "Empty result for new!");
    }

//    @Test
    void excelDownloadTest() {
        //String filePath = "/resources/file/reports/ee9c1eeb-cef2-472d-b6d0-ddeb015a370b/profiles.xlsx";
        String filePath = "/resources/file/accounts.xlsx";
        BankStatementImportUseCase.ImportBankStatementsCommand command =
                new BankStatementImportUseCase.ImportBankStatementsCommand(
                        "TAB12345", filePath);

        List<BankStatementImportReconciliationInfo> result = bankStatementImportUseCase.importStatements(command);
        Assertions.assertTrue(result.size() > 0, "Empty result for excel file!");
    }
}
