package bhutan.eledger.application.port.out.eledger.config.glaccount;

public interface GetGlAccountPartFullCodeOnlyPort {

    GlAccountPartFullCodeOnly getGlAccountPartFullCodeOnly(Long id);

    interface GlAccountPartFullCodeOnly {
        String getFullCode();
    }
}
