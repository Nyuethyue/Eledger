package bhutan.eledger.configuration.http;

import com.jsunsoft.http.ClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DefaultHttpClientConfig {

    @Bean("defaultApacheHttpClient")
    CloseableHttpClient apacheHttpClient() {
        return ClientBuilder.create()
                .build();
    }
}
