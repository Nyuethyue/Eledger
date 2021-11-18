package bhutan.eledger.common.excel;

import bhutan.eledger.domain.epayment.BankStatementImportReconciliationInfo;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class ReconciliationExcelLoader  implements ExcelCellReceiver {
    private List<BankStatementImportReconciliationInfo> result = new LinkedList<>();

    public List<BankStatementImportReconciliationInfo> load(InputStream inputStream, boolean isXLSX) {
        if (isXLSX) {
            XLSLoader xlsLoader = new XLSLoader();
            xlsLoader.load(inputStream, "rId1", this);
        } else {
            XLSXLoader xlsxLoader = new XLSXLoader();
            xlsxLoader.load(inputStream, "rId1", this);
        }
        return result;
    }

    @Override
    public void newCell(String sheetId, int row, int column, String type, String value) {
        System.out.println("Row:" + row + " Col:" + column + " Value:" + value + " Type:" + type);
    }
}
