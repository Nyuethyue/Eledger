package bhutan.eledger.adapter.out.epayment.fms.reconciliation;

import bhutan.eledger.application.port.out.epayment.reconciliation.BankStatementFileParserPort;
import bhutan.eledger.configuration.fms.FmsProperties;
import bhutan.eledger.domain.epayment.deposit.BankStatementImportReconciliationInfo;
import com.jsunsoft.http.HttpRequest;
import com.jsunsoft.http.HttpRequestBuilder;
import com.jsunsoft.http.Response;
import com.jsunsoft.http.WebTarget;
import lombok.extern.log4j.Log4j2;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.List;

@Log4j2
@Component
class BankStatementFileParserAdapter implements BankStatementFileParserPort {

    private final HttpRequest httpRequest;
    private final FmsProperties fmsProperties;

    BankStatementFileParserAdapter(CloseableHttpClient httpClient, FmsProperties fmsProperties) {
        httpRequest = HttpRequestBuilder.create(httpClient)
                .build();
        this.fmsProperties = fmsProperties;
    }

    @Override
    public List<BankStatementImportReconciliationInfo> getStatements(String filePath) {
        String lcFilePath = filePath.toLowerCase();
        if (!lcFilePath.endsWith(".xls") && !lcFilePath.endsWith(".xlsx")) {
            throw new InvalidParameterException("Invalid excel file type extension:" + filePath);
        }

        WebTarget webTarget = httpRequest.target(fmsProperties.getUri())
                .path(filePath);

        try (Response response = webTarget.get()) {
            if (response.isSuccess() && response.hasEntity()) {
                try (InputStream inputStream = response.getEntity().getContent()) {
                    ReconciliationExcelLoader loader = new ReconciliationExcelLoader();
                    return loader.load(inputStream, lcFilePath.endsWith(".xlsx"));
                } catch (SAXException | ParserConfigurationException | OpenXML4JException e) {
                    throw new RuntimeException(MessageFormat.format("Invalid excel file from:{0}" , webTarget.getURIString()), e);
                }
            } else {
                throw new RuntimeException(MessageFormat.format("Cant read excel file from:{0}, Http.status:{1}", webTarget.getURIString(), response.getStatusCode()));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(MessageFormat.format("Failed to retrieve file from:{0}" ,  webTarget.getURIString()), e);
        }
    }
}
