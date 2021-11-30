package bhutan.eledger.domain.ref.denomination;

import lombok.Data;


@Data(staticConstructor = "withId")
public class RefDenomination {
    private final Long id;
    private final String value;

    public static RefDenomination withoutId(
            String value
    ) {
        return new RefDenomination(
                null,
                value
        );
    }
}
