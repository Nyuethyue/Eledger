package bhutan.eledger.common.excel;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;

@Log4j2
public class XLSXLoader extends DefaultHandler {

    private ExcelCellReceiver receiver;
    private SharedStringsTable sst;
    private String lastContents;
    private CellReference cellReferenceObject;
    private String cellType;
    private boolean nextIsString;
    private String sheetId;
    public void load(InputStream io, String sheetId, ExcelCellReceiver receiver) throws Exception {
        try(OPCPackage pkg = OPCPackage.open(io)) {
            this.receiver = receiver;
            XSSFReader r = new XSSFReader(pkg);
            sst = r.getSharedStringsTable();

            XMLReader parser = XMLHelper.newXMLReader();

            parser.setContentHandler(this);
            InputStream sheet = r.getSheet(sheetId);
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        if(name.equals("c")) {
            String cellReference = attributes.getValue("r");
            cellReferenceObject = new CellReference(cellReference);
            // Figure out if the value is an index in the SST
            cellType = attributes.getValue("t");
            if(cellType != null && cellType.equals("s")) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }
        // Clear contents cache
        lastContents = "";
    }
    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        if(nextIsString) {
            int idx = Integer.parseInt(lastContents);
            lastContents = sst.getItemAt(idx).getString();
            nextIsString = false;
        }

        if(name.equals("v")) {
            receiver.newCell(sheetId, cellReferenceObject.getRow(), cellReferenceObject.getCol(), cellType, lastContents);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        lastContents += new String(ch, start, length);
    }

    @Override
    public void startDocument ()
            throws SAXException
    {
        receiver.startDocument();
    }

    @Override
    public void endDocument ()
            throws SAXException
    {
        receiver.endDocument();
    }
}
