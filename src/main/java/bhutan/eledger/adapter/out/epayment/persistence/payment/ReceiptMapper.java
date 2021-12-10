package bhutan.eledger.adapter.out.epayment.persistence.payment;

import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.domain.epayment.payment.Payment;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
import bhutan.eledger.domain.epayment.payment.Receipt;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class ReceiptMapper {

    ReceiptEntity mapToEntity(Receipt receipt) {
        ReceiptEntity receiptEntity = new ReceiptEntity(
                receipt.getId(),
                receipt.getDrn(),
                receipt.getPaymentMode().getValue(),
                receipt.getStatus().getValue(),
                receipt.getCurrency().getId(),
                receipt.getBankBranch() != null ? receipt.getBankBranch().getId() : null,
                receipt.getReceiptNumber(),
                receipt.getSecurityNumber(),
                receipt.getInstrumentNumber(),
                receipt.getInstrumentDate(),
                receipt.getOtherReferenceNumber(),
                receipt.getCreationDateTime(),
                receipt.getTotalPaidAmount(),
                receipt.getPan(),
                receipt.getTaxpayer()
        );

        receiptEntity.setPayments(
                receipt.getPayments()
                        .stream()
                        .map(payment ->
                                new PaymentEntity(
                                        payment.getId(),
                                        payment.getPayableLineId(),
                                        payment.getElTargetTransactionId(),
                                        payment.getPaidAmount(),
                                        payment.getGlAccount(),
                                        receiptEntity
                                )
                        )
                        .collect(Collectors.toSet())
        );

        return receiptEntity;
    }

    Receipt mapToDomain(ReceiptEntity receipt, RefEntry refEntry) {
        return Receipt.withId(
                receipt.getId(),
                receipt.getDrn(),
                PaymentMode.of(receipt.getPaymentMode()),
                ReceiptStatus.of(receipt.getStatus()),
                refEntry,
                receipt.getReceiptNumber(),
                receipt.getCreationDateTime(),
                receipt.getTaxpayer(),
                receipt.getPayments()
                        .stream()
                        .map(pe ->
                                Payment.withId(
                                        pe.getId(),
                                        pe.getGlAccount(),
                                        pe.getPaidAmount(),
                                        pe.getPayableLineId(),
                                        pe.getElTargetTransactionId()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet()),
                receipt.getTotalPaidAmount(),
                receipt.getSecurityNumber(),
                receipt.getInstrumentNumber(),
                receipt.getInstrumentDate(),
                receipt.getOtherReferenceNumber(),
                RefEntry.builder(receipt.getRefBankBranchId(), "test").build(), //todo
                receipt.getPan()
        );
    }
}
