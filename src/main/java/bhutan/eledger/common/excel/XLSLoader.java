package bhutan.eledger.common.excel;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class XLSLoader implements HSSFListener {
    private String sheetName;
    private ExcelCellReceiver receiver;
    private SSTRecord sstrec;
    private String sheetId;
    private DataFormatter formatter = new DataFormatter();

    public void load1(InputStream io, String sheetId, ExcelCellReceiver receiver) throws IOException {
        this.sheetId = sheetId;
        this.receiver = receiver;
        try (POIFSFileSystem poifs = new POIFSFileSystem(io)) {
            // get the Workbook (excel part) stream in a InputStream
            InputStream din = poifs.createDocumentInputStream("Workbook");
            // construct out HSSFRequest object
            HSSFRequest req = new HSSFRequest();
            // lazy listen for ALL records with the listener shown above
            req.addListenerForAllRecords(this);
            // create our event factory
            HSSFEventFactory factory = new HSSFEventFactory();
            // process our events based on the document input stream

            receiver.startDocument();
            factory.processEvents(req, din);
            receiver.endDocument();

            // and our document input stream (don't want to leak these!)
            din.close();

        }
    }

    public void load(InputStream io, String sheetId, ExcelCellReceiver receiver) throws IOException {
        this.sheetId = sheetId;
        this.receiver = receiver;
        try (HSSFWorkbook workbook = new HSSFWorkbook(io)) {
            //getting the first sheet from the workbook using sheet name.
            // We can also pass the index of the sheet which starts from '0'.
            HSSFSheet sheet = workbook.getSheet("Sheet1");
            HSSFRow row;
            HSSFCell cell;

            //Iterating all the rows in the sheet
            Iterator rows = sheet.rowIterator();

            receiver.startDocument();
            while (rows.hasNext()) {
                row = (HSSFRow) rows.next();

                //Iterating all the cells of the current row
                Iterator cells = row.cellIterator();

                while (cells.hasNext()) {
                    cell = (HSSFCell) cells.next();
                    if (cell.getCellType() == CellType.STRING) {
                        receiver.newCell(sheetId, cell.getRowIndex(), cell.getColumnIndex(), "v", cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC) {
                        String strValue = formatter.formatCellValue(cell);
                        receiver.newCell(sheetId, cell.getRowIndex(), cell.getColumnIndex(), "v", strValue);
//
//
//                        String stringNumeric = Double.toString(cell.getNumericCellValue());
//                        receiver.newCell(sheetId, cell.getRowIndex(), cell.getColumnIndex(), "v", stringNumeric);
                    } else if (cell.getCellType() == CellType.BOOLEAN) {
                        String stringBoolen = Boolean.toString(cell.getBooleanCellValue());
                        receiver.newCell(sheetId, cell.getRowIndex(), cell.getColumnIndex(), "v", stringBoolen);
                    } else {
                    }
                }
            }
            receiver.endDocument();
        }
    }

    /**
     * This method listens for incoming records and handles them as required.
     *
     * @param record The record that was found while reading.
     */

    @Override
    public void processRecord(Record record) {
        switch (record.getSid()) {
            // the BOFRecord can represent either the beginning of a sheet or the workbook
            case BOFRecord.sid:
                BOFRecord bof = (BOFRecord) record;
                if (bof.getType() == bof.TYPE_WORKBOOK) {
                    System.out.println("Encountered workbook");
                    // assigned to the class level member
                } else if (bof.getType() == bof.TYPE_WORKSHEET) {
                    System.out.println("Encountered sheet reference");
                }
                break;
            case BoundSheetRecord.sid:
                BoundSheetRecord bsr = (BoundSheetRecord) record;
                sheetName = bsr.getSheetname();
                System.out.println("New sheet named: " + bsr.getSheetname());
                break;
            case RowRecord.sid:
                RowRecord rowrec = (RowRecord) record;
                System.out.println("Row found, first column at "
                        + rowrec.getFirstCol() + " last column at " + rowrec.getLastCol());
                break;
            case NumberRecord.sid:
                NumberRecord numrec = (NumberRecord) record;
                try {
                    receiver.newCell(sheetName, numrec);
                } catch (Exception ignore) {

                }
                System.out.println("Cell found with value " + numrec.getValue()
                        + " at row " + numrec.getRow() + " and column " + numrec.getColumn());
                break;
            // SSTRecords store a array of unique strings used in Excel.
            case SSTRecord.sid:
                sstrec = (SSTRecord) record;
                for (int k = 0; k < sstrec.getNumUniqueStrings(); k++) {
                    System.out.println("String table value " + k + " = " + sstrec.getString(k));
                }
                break;
            case LabelSSTRecord.sid:
                LabelSSTRecord lrec = (LabelSSTRecord) record;
                receiver.newCell(sheetName, lrec.getRow(), lrec.getColumn(), "i", sstrec.getString(lrec.getSSTIndex()).getString());
                System.out.println("String cell found with value "
                        + sstrec.getString(lrec.getSSTIndex()));
                break;
        }
    }
}
