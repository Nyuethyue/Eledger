package bhutan.eledger.epayment.payment;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchPaymentAdviceUseCase;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.common.excel.ExcelLoader;
import bhutan.eledger.domain.epayment.PaymentAdvice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.In;
import org.springframework.test.context.TestPropertySource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        ExcelLoader loader = new ExcelLoader();
        try {
            Path resourceDirectory = Paths.get("src","test","resources", "files");
            String absolutePath = resourceDirectory.toFile().getAbsolutePath();

            FileInputStream fis = new FileInputStream( absolutePath + "\\" + "Reconciliation upload file.xlsx");
            loader.load(fis, "rId1", (row, column, type, value) ->
                    System.out.println("Row:" + row + " Col:" + column + " Value:" + value + " Type:" + type));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(1, 1);
    }
}
