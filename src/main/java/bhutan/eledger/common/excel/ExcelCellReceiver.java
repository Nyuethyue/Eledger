package bhutan.eledger.common.excel;

public interface ExcelCellReceiver {
    void startDocument();

    void newCell(int sheetIndex, int row, int column, String value);

    void endDocument();
}
