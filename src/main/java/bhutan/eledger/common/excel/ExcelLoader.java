package bhutan.eledger.common.excel;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
@NoArgsConstructor
public  class ExcelLoader {
    public void load(InputStream inputStream, ExcelCellReceiver receiver, boolean isXLSX)
            throws IOException, SAXException, OpenXML4JException, ParserConfigurationException {
        if (isXLSX) {
            XLSXLoader xlsxLoader = new XLSXLoader();
            xlsxLoader.load(inputStream, 0, receiver);
        } else {
            XLSLoader xlsLoader = new XLSLoader();
            xlsLoader.load(inputStream, 0, receiver);
        }
    }
}
