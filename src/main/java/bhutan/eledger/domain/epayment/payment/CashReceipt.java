package bhutan.eledger.domain.epayment.payment;

import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@ToString
public class CashReceipt extends Receipt {

    private CashReceipt(
            Long id,
            String drn,
            PaymentMode paymentMode,
            ReceiptStatus status,
            String currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments
    ) {
        super(id, drn, paymentMode, status, currency, receiptNumber, creationDateTime, taxpayer, payments);
    }

    public static CashReceipt withId(
            Long id,
            String drn,
            PaymentMode paymentMode,
            ReceiptStatus status,
            String currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments
    ) {
        return new CashReceipt(
                id,
                drn,
                paymentMode,
                status,
                currency,
                receiptNumber,
                creationDateTime,
                taxpayer,
                payments
        );
    }

    public static CashReceipt withoutId(
            String drn,
            PaymentMode paymentMode,
            ReceiptStatus status,
            String currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments
    ) {
        return new CashReceipt(
                null,
                drn,
                paymentMode,
                status,
                currency,
                receiptNumber,
                creationDateTime,
                taxpayer,
                payments
        );
    }
}
