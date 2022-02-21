package bhutan.eledger.configuration.epayment.rma;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "bhutan.eledger.rma")
@ConstructorBinding
@Data
@Validated
public class RmaProperties {
    private final Api api;
    private final Sign sign;
    private final Beneficiary beneficiary;
    private final Task task;

    @Data
    public static class Sign {
        @NotNull
        private final Resource ksResource;
        private final String ksPassword;
        private final String ksType;
        private final String keyPassword;
        private final String keyAlias;
        private final String algorithm;

        public Sign(Resource ksLocation, String ksPassword, String ksType, String keyPassword, String keyAlias, String algorithm) {
            this.ksResource = ksLocation;
            this.ksPassword = ksPassword;
            this.ksType = ksType;
            this.keyPassword = keyPassword;
            this.keyAlias = keyAlias;
            this.algorithm = algorithm == null ? "SHA1withRSA" : algorithm;
        }
    }

    @Data
    public static class Api {
        private final String host;

        @NotNull
        private final String arWebPath;
        private final String asCheckStatusPath;
        private final String arMobilePath;
        @NotNull
        private final Double version;
    }

    @Data
    public static class Beneficiary {
        private final String id;
        private final String bankCode;
    }

    @Data
    public static class Task {
        private final Integer retryCountUntilFail;
    }
}
