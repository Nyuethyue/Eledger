package bhutan.eledger.domain.epayment.payment;

import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public abstract class Receipt {
    private final Long id;
    private final String drn;
    private final PaymentMode paymentMode;
    private final ReceiptStatus status;
    private final RefEntry currency;
    private final String receiptNumber;
    private final LocalDateTime creationDateTime;

    private final EpTaxpayer taxpayer;
    private final Collection<Payment> payments;

    public BigDecimal getTotalPaidAmount() {
        return payments
                .stream()
                .map(Payment::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
