package bhutan.eledger.domain.ref.bankaccount;

import lombok.Data;

@Data(staticConstructor = "withId")
public class BankAccountGLAccountPart {
    private final Long id;
    private final String code;

    public static BankAccountGLAccountPart withoutId(
            String code
    ) {
        return new BankAccountGLAccountPart(
                null,
                code

        );
    }
}
