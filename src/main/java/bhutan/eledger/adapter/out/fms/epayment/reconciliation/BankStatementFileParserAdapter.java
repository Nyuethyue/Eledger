package bhutan.eledger.adapter.fms.epayment.reconciliation;

import bhutan.eledger.application.port.in.epayment.reconciliation.BankStatementImportUseCase;
import bhutan.eledger.application.port.out.epayment.reconciliation.BankStatementFileParserPort;
import bhutan.eledger.common.excel.ReconciliationExcelLoader;
import bhutan.eledger.configuration.fms.FmsProperties;
import bhutan.eledger.domain.epayment.BankStatementImportReconciliationInfo;
import com.jsunsoft.http.HttpRequest;
import com.jsunsoft.http.HttpRequestBuilder;
import com.jsunsoft.http.Response;
import com.jsunsoft.http.WebTarget;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.security.InvalidParameterException;
import java.util.List;

@Component
class BankStatementFileParserAdapter implements BankStatementFileParserPort {

    private final HttpRequest httpRequest;
    private final FmsProperties fmsProperties;

    BankStatementFileParserAdapter(CloseableHttpClient httpClient, FmsProperties fmsProperties) {
        httpRequest = HttpRequestBuilder.create(httpClient)
//                .addDefaultHeader() todo for Aleksandr add header or remove
                .build();
        this.fmsProperties = fmsProperties;
    }

    @Override
    public List<BankStatementImportReconciliationInfo> getStatements(String filePath) {
        WebTarget webTarget = httpRequest.target(fmsProperties.getUri())
                .path(filePath);

        try (Response response = webTarget.get()) {
            filePath = filePath.toLowerCase();
            if (!filePath.endsWith(".xls") && filePath.endsWith(".xlsx")) {
                throw new InvalidParameterException("Invalid excel file type extension:" + filePath);
            }

            if (response.isSuccess() && response.hasEntity()) {
                try (InputStream inputStream = response.getEntity().getContent()) {
                    ReconciliationExcelLoader loader = new ReconciliationExcelLoader();
                    return loader.load(inputStream, filePath.endsWith(".xlsx"));
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read file from: " + webTarget.getURIString(), e);
        }
        return null;
    }
}
