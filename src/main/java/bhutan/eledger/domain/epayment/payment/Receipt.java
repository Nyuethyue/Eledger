package bhutan.eledger.domain.epayment.payment;

import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Builder(toBuilder = true)
public class Receipt {
    private final Long id;
    private final String drn;
    private final PaymentMode paymentMode;
    private final ReceiptStatus status;
    private final RefEntry currency;
    private final String receiptNumber;
    private final LocalDateTime creationDateTime;
    private final EpTaxpayer taxpayer;
    private final Collection<Payment> payments;
    private final BigDecimal totalPaidAmount;
    private final String securityNumber;
    private final String instrumentNumber;
    private final LocalDate instrumentDate;
    private final String otherReferenceNumber;
    private final RefEntry bankBranch;
    private final String pan;

    public static Receipt withId(
            Long id,
            String drn,
            PaymentMode paymentMode,
            ReceiptStatus status,
            RefEntry currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments,
            BigDecimal totalPaidAmount,
            String securityNumber,
            String instrumentNumber,
            LocalDate instrumentDate,
            String otherReferenceNumber,
            RefEntry bankBranch,
            String pan
    ) {
        return new Receipt(
                id,
                drn,
                paymentMode,
                status,
                currency,
                receiptNumber,
                creationDateTime,
                taxpayer,
                payments,
                totalPaidAmount,
                securityNumber,
                instrumentNumber,
                instrumentDate,
                otherReferenceNumber,
                bankBranch,
                pan
        );
    }

    public static Receipt withoutId(
            String drn,
            PaymentMode paymentMode,
            ReceiptStatus status,
            RefEntry currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments,
            BigDecimal totalPaidAmount,
            String securityNumber,
            String instrumentNumber,
            LocalDate instrumentDate,
            String otherReferenceNumber,
            RefEntry bankBranch,
            String pan
    ) {
        return new Receipt(
                null,
                drn,
                paymentMode,
                status,
                currency,
                receiptNumber,
                creationDateTime,
                taxpayer,
                payments,
                totalPaidAmount,
                securityNumber,
                instrumentNumber,
                instrumentDate,
                otherReferenceNumber,
                bankBranch,
                pan
        );
    }

    public static Receipt chequeWithoutId(
            String drn,
            PaymentMode paymentMode,
            ReceiptStatus status,
            RefEntry currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments,
            BigDecimal totalPaidAmount,
            String securityNumber,
            String instrumentNumber,
            LocalDate instrumentDate,
            String otherReferenceNumber,
            RefEntry bankBranch,
            String pan
    ) {
        return new Receipt(
                null,
                drn,
                paymentMode,
                status,
                currency,
                receiptNumber,
                creationDateTime,
                taxpayer,
                payments,
                totalPaidAmount,
                securityNumber,
                instrumentNumber,
                instrumentDate,
                otherReferenceNumber,
                bankBranch,
                pan
        );
    }

    public static Receipt cashWithoutId(
            String drn,
            PaymentMode paymentMode,
            ReceiptStatus status,
            RefEntry currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments,
            BigDecimal totalPaidAmount,
            String securityNumber,
            String pan
    ) {
        return new Receipt(
                null,
                drn,
                paymentMode,
                status,
                currency,
                receiptNumber,
                creationDateTime,
                taxpayer,
                payments,
                totalPaidAmount,
                securityNumber,
                null,
                null,
                null,
                null,
                pan
        );
    }
}
