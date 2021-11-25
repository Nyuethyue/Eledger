package bhutan.eledger.common.excel;

import org.apache.poi.hssf.record.CellRecord;

public interface ExcelCellReceiver {
    void startDocument();

    void newCell(String sheetId, int row, int column, String type, String value);
    void newCell(String sheetId, CellRecord record);

    void endDocument();
}
