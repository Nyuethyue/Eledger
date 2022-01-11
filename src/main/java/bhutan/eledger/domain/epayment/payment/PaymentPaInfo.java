package bhutan.eledger.domain.epayment.payment;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentPaInfo implements Comparable<PaymentPaInfo> {
    private final Long id;
    @EqualsAndHashCode.Include
    private final Long paId;
    private final String pan;
    private final String drn;

    public static PaymentPaInfo withId(
            Long id,
            Long paId,
            String pan,
            String drn
    ) {
        return new PaymentPaInfo(
                id,
                paId,
                pan,
                drn
        );
    }

    public static PaymentPaInfo withoutId(
            Long paId,
            String pan,
            String drn
    ) {
        return new PaymentPaInfo(
                null,
                paId,
                pan,
                drn
        );
    }

    @Override
    public int compareTo(PaymentPaInfo o) {
        return paId.compareTo(o.getPaId());
    }
}
