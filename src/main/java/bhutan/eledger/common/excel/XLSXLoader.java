package bhutan.eledger.common.excel;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.XMLHelper;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class XLSXLoader extends DefaultHandler {
    private int sheetIndex;
    private ExcelCellReceiver receiver;
    private SharedStringsTable sst;
    private String lastContents;
    private CellReference cellReferenceObject;
    private String cellType;
    private boolean nextIsString;

    public void load(InputStream io, int sheetIndex, ExcelCellReceiver receiver) throws IOException, SAXException, ParserConfigurationException, OpenXML4JException {
        try (OPCPackage pkg = OPCPackage.open(io)) {
            this.sheetIndex = sheetIndex;
            this.receiver = receiver;
            XSSFReader r = new XSSFReader(pkg);
            sst = r.getSharedStringsTable();
            XMLReader parser = XMLHelper.newXMLReader();
            parser.setContentHandler(this);
            InputStream sheet = r.getSheet("rId" + (sheetIndex + 1));
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        if (name.equals("c")) {
            String cellReference = attributes.getValue("r");
            cellReferenceObject = new CellReference(cellReference);
            // Figure out if the value is an index in the SST
            cellType = attributes.getValue("t");
            nextIsString = cellType != null && cellType.equals("s");
        }
        // Clear contents cache
        lastContents = "";
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        if (nextIsString) {
            int idx = Integer.parseInt(lastContents);
            lastContents = sst.getItemAt(idx).getString();
            nextIsString = false;
        }
        if (name.equals("v")) {
            receiver.newCell(sheetIndex, cellReferenceObject.getRow(), cellReferenceObject.getCol(), lastContents);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        lastContents += new String(ch, start, length);
    }

    @Override
    public void startDocument() throws SAXException {
        receiver.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        receiver.endDocument();
    }
}