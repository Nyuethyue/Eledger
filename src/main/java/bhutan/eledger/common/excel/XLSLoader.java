package bhutan.eledger.common.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XLSLoader {
    private final DataFormatter formatter = new DataFormatter();

    public void load(InputStream io, int sheetIndex, ExcelCellReceiver receiver) throws IOException {
        try (HSSFWorkbook workbook = new HSSFWorkbook(io)) {
            //getting the first sheet from the workbook using sheet name.
            // We can also pass the index of the sheet which starts from '0'.
            HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
            HSSFRow row;
            HSSFCell cell;
            //Iterating all the rows in the sheet
            Iterator<Row> rows = sheet.rowIterator();
            receiver.startDocument();
            while (rows.hasNext()) {
                row = (HSSFRow) rows.next();
                //Iterating all the cells of the current row
                Iterator<Cell> cells = row.cellIterator();
                while (cells.hasNext()) {
                    cell = (HSSFCell) cells.next();
                    if (cell.getCellType() == CellType.STRING) {
                        receiver.newCell(sheetIndex, cell.getRowIndex(), cell.getColumnIndex(), cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        String strValue = formatter.formatCellValue(cell);
                        receiver.newCell(sheetIndex, cell.getRowIndex(), cell.getColumnIndex(), strValue);
                    } else if (cell.getCellType() == CellType.BOOLEAN) {
                        String stringBoolen = Boolean.toString(cell.getBooleanCellValue());
                        receiver.newCell(sheetIndex, cell.getRowIndex(), cell.getColumnIndex(), stringBoolen);
                    }
                }
            }
            receiver.endDocument();
        }
    }
}
