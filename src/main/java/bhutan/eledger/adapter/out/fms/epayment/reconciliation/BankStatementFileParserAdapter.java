package bhutan.eledger.adapter.out.fms.epayment.reconciliation;

import bhutan.eledger.application.port.out.epayment.reconciliation.BankStatementFileParserPort;
import bhutan.eledger.configuration.fms.FmsProperties;
import com.jsunsoft.http.HttpRequest;
import com.jsunsoft.http.HttpRequestBuilder;
import com.jsunsoft.http.Response;
import com.jsunsoft.http.WebTarget;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

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
    public void getStatements(String fileId) {

        WebTarget webTarget = httpRequest.target(fmsProperties.getUri())
                .path("path")//todo for Aleksandr specify real path
                .addParameter("key", "Value") //todo for Aleksandr specify parameters if needed or remove
                ;

        try (Response response = webTarget.get()) {
            if (response.isSuccess() && response.hasEntity()) {
                InputStream inputStream = response.getEntity().getContent();

                //todo for Aleksandr parse input stream
            }

        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read file from: " + webTarget.getURIString(), e);
        }
    }
}
