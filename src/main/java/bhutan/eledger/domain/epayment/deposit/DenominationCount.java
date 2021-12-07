package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;

@Data
public class DenominationCount {
    private final Long id;
    private final Long denominationId;
    private final Long denominationCount;

    public static DenominationCount withoutId(
            Long denominationId,
            Long denominationCount
    ) {
        return new DenominationCount(
                null,
                denominationId,
                denominationCount
        );
    }
}
