package bhutan.eledger.application.service.epayment.payment;

import bhutan.eledger.domain.epayment.payment.Payment;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
class ReceiptCreationContext {
    private final Map<Long, PaymentAdvice> idToPaymentAdvice;
    private final List<PaymentAdvice> updatedPaymentAdvices;
    private final boolean isAllPaid;
    private final Collection<Payment> payments;


    ReceiptCreationContext(Map<Long, PaymentAdvice> idToPaymentAdvice, List<PaymentAdvice> updatedPaymentAdvices, boolean isAllPaid, Collection<Payment> payments) {
        Assert.notEmpty(updatedPaymentAdvices, "updatedPaymentAdvices list can't be empty");
        Assert.notEmpty(idToPaymentAdvice, "idToPaymentAdvice list can't be empty");
        Assert.notEmpty(payments, "payments list can't be empty");
        this.idToPaymentAdvice = idToPaymentAdvice;
        this.updatedPaymentAdvices = updatedPaymentAdvices;
        this.isAllPaid = isAllPaid;
        this.payments = payments;
    }

    public BigDecimal getTotalPaidAmount() {
        return payments
                .stream()
                .map(Payment::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    PaymentAdvice getAnyPa() {
        return CollectionUtils.firstElement(updatedPaymentAdvices);
    }

}
