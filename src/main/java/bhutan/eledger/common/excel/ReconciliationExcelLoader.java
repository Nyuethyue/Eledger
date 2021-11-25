package bhutan.eledger.common.excel;

import bhutan.eledger.domain.epayment.BankStatementImportReconciliationInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.DateUtil;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
@Log4j2
public class ReconciliationExcelLoader  implements ExcelCellReceiver {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "d/M/yy" );
    private int currentRowIndex;
    private BankStatementImportReconciliationInfo currentRowValue;

    private List<BankStatementImportReconciliationInfo> result;


    public List<BankStatementImportReconciliationInfo> load(InputStream inputStream, boolean isXLSX) throws Exception {
        try {
            if (isXLSX) {
                XLSXLoader xlsxLoader = new XLSXLoader();
                xlsxLoader.load(inputStream,0,this);
            } else {
                XLSLoader xlsLoader = new XLSLoader();
                xlsLoader.load(inputStream, 0, this);
            }
        } catch (Exception e) {
            log.error("Failed to parse excel file");
            throw new Exception("Error loading excel file", e);
        }
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
                if(value.contains("/")) {
                    currentRowValue.setPaymentDate(LocalDate.parse( value , formatter ));
                } else
                {
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
