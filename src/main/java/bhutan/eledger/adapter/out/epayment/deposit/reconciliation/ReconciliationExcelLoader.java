package bhutan.eledger.adapter.out.epayment.deposit.reconciliation;

import bhutan.eledger.common.excel.ExcelCellReceiver;
import bhutan.eledger.common.excel.ExcelLoader;
import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadRecordInfo;
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
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private int currentRowIndex;
    private ReconciliationUploadRecordInfo currentRowValue;

    private List<ReconciliationUploadRecordInfo> result;

    public List<ReconciliationUploadRecordInfo> load(InputStream inputStream, boolean isXLSX)
            throws IOException, SAXException, OpenXML4JException, ParserConfigurationException {
        ExcelLoader.getInstance().load(inputStream, this, isXLSX);
        return result;
    }

    @Override
    public void newCell(int sheetIndex, int row, int column, String value) {
        if (row > 0) { // 0 is table header
            if (currentRowIndex != row) {
                if (null != currentRowValue) {
                    result.add(currentRowValue);
                }
                currentRowValue = new ReconciliationUploadRecordInfo();
            }

            if (0 == column) {
                currentRowValue.setTransactionId(value);
            } else if (1 == column) {
                currentRowValue.setBankBranchCode(value);
            } else if (2 == column) {
                currentRowValue.setDepositNumber(value);
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
