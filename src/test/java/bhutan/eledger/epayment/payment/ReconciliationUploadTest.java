package bhutan.eledger.epayment.payment;

import bhutan.eledger.common.excel.XLSLoader;
import bhutan.eledger.common.excel.XLSXLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    void excelLoadTest() throws FileNotFoundException, IOException {

            Path resourceDirectory = Paths.get("src","test","resources", "files");
            String absolutePath = resourceDirectory.toFile().getAbsolutePath();

            XLSXLoader xlsxLoader = new XLSXLoader();
            try (FileInputStream fis = new FileInputStream( absolutePath + "\\" + "Reconciliation.xlsx")) {
                xlsxLoader.load(fis, "rId1", (sheetId, row, column, type, value) ->
                        System.out.println("Row:" + row + " Col:" + column + " Value:" + value + " Type:" + type));
            }

            XLSLoader xlsLoader = new XLSLoader();
            try (FileInputStream fis = new FileInputStream( absolutePath + "\\" + "Reconciliation.xls")) {
                xlsLoader.load(fis, "rId1", (sheetId, row, column, type, value) ->
                    System.out.println("Row:" + row + " Col:" + column + " Value:" + value + " Type:" + type));
        }

    }
}
