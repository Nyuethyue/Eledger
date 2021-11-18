package bhutan.eledger.common.excel;

public interface ExcelCellReceiver {
    void newCell(String sheetId, int row, int column, String type, String value);
}
