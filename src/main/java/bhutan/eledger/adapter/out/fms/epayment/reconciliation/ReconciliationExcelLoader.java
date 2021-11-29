package bhutan.eledger.adapter.out.fms.epayment.reconciliation;

import bhutan.eledger.common.excel.ExcelCellReceiver;
import bhutan.eledger.common.excel.ExcelLoader;
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
public class ReconciliationExcelLoader  implements ExcelCellReceiver {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yy");
    private int currentRowIndex;
    private BankStatementImportReconciliationInfo currentRowValue;

    private List<BankStatementImportReconciliationInfo> result;

    public List<BankStatementImportReconciliationInfo> load(InputStream inputStream, boolean isXLSX)
            throws IOException, SAXException, OpenXML4JException, ParserConfigurationException {
        ExcelLoader loader = new ExcelLoader();
        loader.load(inputStream, this, isXLSX);
        return result;
    }

    @Override
    public void newCell(int sheetIndex, int row, int column, String value) {
        if (row > 0) { // 0 is table header
            if (currentRowIndex != row) {
                if (null != currentRowValue) {
                    result.add(currentRowValue);
                }
                currentRowValue = new BankStatementImportReconciliationInfo();
            }

            if (0 == column) {
                currentRowValue.setTransactionId(value);
            } else if (1 == column) {
                currentRowValue.setBankId(value);
            } else if (2 == column) {
                currentRowValue.setRefNo(value);
            } else if (3 == column) {
                if (value.contains("/")) {
                    currentRowValue.setPaymentDate(LocalDate.parse(value, formatter));
                } else {
                    double excelDate = Double.parseDouble(value);
                    currentRowValue.setPaymentDate(DateUtil.getLocalDateTime(excelDate).toLocalDate());
                }
            } else if (4 == column) {
                currentRowValue.setAmount(new BigDecimal(value));
            }
            currentRowIndex = row;
        }
    }

    @Override
    public void startDocument() {
        currentRowIndex = -1;
        currentRowValue = null;
        result = new LinkedList<>();
    }

    @Override
    public void endDocument() {
        result.add(currentRowValue);
    }
}
