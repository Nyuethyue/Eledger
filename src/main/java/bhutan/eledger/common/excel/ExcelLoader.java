package bhutan.eledger.common.excel;

import bhutan.eledger.domain.epayment.BankStatementImportReconciliationInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.DateUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Log4j2
public  class ExcelLoader {
    private ExcelCellReceiver receiver;

    public static void load(InputStream inputStream, ExcelCellReceiver receiver, boolean isXLSX)
            throws IOException, SAXException, OpenXML4JException, ParserConfigurationException {
        if (isXLSX) {
            XLSXLoader xlsxLoader = new XLSXLoader();
            xlsxLoader.load(inputStream, 0, receiver);
        } else {
            XLSLoader xlsLoader = new XLSLoader();
            xlsLoader.load(inputStream, 0, receiver);
        }
    }
}
