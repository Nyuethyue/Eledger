package bhutan.eledger.domain.ref.agencyglaccount;

import lombok.Data;

@Data(staticConstructor = "withId")
public class RefAgencyGLAccount {
    private final Long id;
    private final String code;
    private final Long agencyId;

    public static RefAgencyGLAccount withoutId(
            String code,
            Long agencyId
    ) {
        return new RefAgencyGLAccount(
                null,
                code,
                agencyId
        );
    }
}
