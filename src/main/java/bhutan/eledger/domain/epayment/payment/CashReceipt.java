package bhutan.eledger.domain.epayment.payment;

import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
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
            RefEntry currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments,
            BigDecimal totalPaidAmount
    ) {
        super(id, drn, paymentMode, status, currency, receiptNumber, creationDateTime, taxpayer, payments,totalPaidAmount);
    }

    public static CashReceipt withId(
            Long id,
            String drn,
            PaymentMode paymentMode,
            ReceiptStatus status,
            RefEntry currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments,
            BigDecimal totalPaidAmount
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
                payments,
                totalPaidAmount
        );
    }

    public static CashReceipt withoutId(
            String drn,
            PaymentMode paymentMode,
            ReceiptStatus status,
            RefEntry currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments,
            BigDecimal totalPaidAmount
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
                payments,
                totalPaidAmount
        );
    }
}
