package bhutan.eledger.domain.epayment.payment;

import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@Builder(toBuilder = true)
public class Receipt {
    private final Long id;
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
    private final RefEntry issuingBankBranch;

    public Collection<PaymentPaInfo> getPaInfos() {
        return payments
                .stream()
                .map(Payment::getPaymentAdviceInfo)
                .collect(Collectors.toUnmodifiableSet());
    }

    public static Receipt withId(
            Long id,
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
            RefEntry bankBranch
    ) {
        return new Receipt(
                id,
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
                null
        );
    }

    public static Receipt withoutId(
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
            RefEntry bankBranch
    ) {
        return new Receipt(
                null,
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
                null
        );
    }

    public static Receipt chequeWithoutId(
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
            RefEntry bankBranch
    ) {
        return new Receipt(
                null,
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
                null
        );
    }

    public static Receipt cashWithoutId(
            PaymentMode paymentMode,
            ReceiptStatus status,
            RefEntry currency,
            String receiptNumber,
            LocalDateTime creationDateTime,
            EpTaxpayer taxpayer,
            Collection<Payment> payments,
            BigDecimal totalPaidAmount,
            String securityNumber
    ) {
        return new Receipt(
                null,
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
                null
        );
    }

    public static Receipt cashWarrantWithoutId(
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
            RefEntry issuingBankBranch
    ) {
        return new Receipt(
                null,
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
                issuingBankBranch
        );
    }

}
