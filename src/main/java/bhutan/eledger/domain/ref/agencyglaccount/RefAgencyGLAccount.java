package bhutan.eledger.domain.ref.agencyglaccount;

import lombok.Data;

@Data(staticConstructor = "withId")
public class RefAgencyGLAccount {
    private final Long id;
    private final String code;
    private final String agencyCode;

    public static RefAgencyGLAccount withoutId(
            String code,
            String agencyCode
    ) {
        return new RefAgencyGLAccount(
                null,
                code,
                agencyCode
        );
    }
}
