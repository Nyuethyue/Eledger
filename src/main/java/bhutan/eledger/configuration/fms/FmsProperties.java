package bhutan.eledger.configuration.fms;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.net.URI;

@ConfigurationProperties(prefix = "bhutan.eledger.fms")
@ConstructorBinding
@Getter
@Validated
public class FmsProperties {
    private final URI uri;

    public FmsProperties(URI uri) {
        this.uri = uri;
    }
}
