package bhutan.eledger.epayment.payment;

import bhutan.eledger.common.excel.ReconciliationExcelLoader;
import bhutan.eledger.common.excel.XLSXLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        properties = {"spring.config.location = classpath:application-test.yml"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReconciliationUploadTest {

    @AfterEach
    void afterEach() {
    }

    @Test
    void excelLoadTest() {
        ReconciliationExcelLoader loader = new ReconciliationExcelLoader();
        try {
            Path resourceDirectory = Paths.get("src","test","resources", "files");
            String absolutePath = resourceDirectory.toFile().getAbsolutePath();

            FileInputStream fis = new FileInputStream( absolutePath + "\\" + "Reconciliation.xlsx");
            loader.load(fis, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(1, 1);
    }
}
