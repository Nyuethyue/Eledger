package bhutan.eledger.application.port.in.epayment.generatereceipt;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;

@Data
public class GenerateReceiptCommonCommand {
    @NotNull
    private final Long paymentAdviceId;
    @NotNull
    private final String currency;
    @NotNull
    @NotEmpty
    private final Collection<PaymentCommand> payments;

    @Data
    public static class PaymentCommand {
        @NotNull
        private final Long payableLineId;
        private final BigDecimal paidAmount;
    }
}
