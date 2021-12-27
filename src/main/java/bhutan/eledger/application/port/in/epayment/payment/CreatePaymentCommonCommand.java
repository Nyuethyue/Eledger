package bhutan.eledger.application.port.in.epayment.payment;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Collection;

@Getter
@ToString
@RequiredArgsConstructor
public class CreatePaymentCommonCommand {
    @NotNull
    private final Long paymentAdviceId;
    @NotNull
    @NotEmpty
    private final Collection<PaymentCommand> payments;

    @Data
    public static class PaymentCommand {
        @NotNull
        private final Long payableLineId;
        @NotNull
        @PositiveOrZero
        private final BigDecimal paidAmount;
    }
}
