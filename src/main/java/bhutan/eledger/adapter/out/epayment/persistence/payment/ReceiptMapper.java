package bhutan.eledger.adapter.out.epayment.persistence.payment;

import bhutan.eledger.common.ref.refentry.RefEntry;
import bhutan.eledger.domain.epayment.payment.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
class ReceiptMapper {

    ReceiptEntity mapToEntity(Receipt receipt) {
        ReceiptEntity receiptEntity = new ReceiptEntity(
                receipt.getId(),
                receipt.getPaymentMode().getValue(),
                receipt.getStatus().getValue(),
                receipt.getCurrency().getId(),
                receipt.getBankBranch() != null ? receipt.getBankBranch().getId() : null,
                receipt.getIssuingBankBranch() != null ? receipt.getIssuingBankBranch().getId() : null,
                receipt.getReceiptNumber(),
                receipt.getSecurityNumber(),
                receipt.getInstrumentNumber(),
                receipt.getPosReferenceNumber(),
                receipt.getInstrumentDate(),
                receipt.getOtherReferenceNumber(),
                receipt.getCreationDateTime(),
                receipt.getTotalPaidAmount(),
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
                                        new PaymentPaymentAdviceInfoEntity(
                                                payment.getPaymentAdviceInfo().getId(),
                                                payment.getPaymentAdviceInfo().getPaId(),
                                                payment.getPaymentAdviceInfo().getPan(),
                                                payment.getPaymentAdviceInfo().getDrn()
                                        ),
                                        receiptEntity
                                )
                        )
                        .collect(Collectors.toSet())
        );

        return receiptEntity;
    }

    Receipt mapToDomain(ReceiptEntity receipt, RefEntry refCurrencyEntry, RefEntry refBankAccountEntry,RefEntry refIssuingBankAccountEntry) {
        return Receipt.withId(
                receipt.getId(),
                PaymentMode.of(receipt.getPaymentMode()),
                ReceiptStatus.of(receipt.getStatus()),
                refCurrencyEntry,
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
                                        pe.getElTargetTransactionId(),
                                        PaymentPaInfo.withId(
                                                pe.getPaymentAdviceInfo().getId(),
                                                pe.getPaymentAdviceInfo().getPaId(),
                                                pe.getPaymentAdviceInfo().getPan(),
                                                pe.getPaymentAdviceInfo().getDrn()
                                        )
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet()),
                receipt.getTotalPaidAmount(),
                receipt.getSecurityNumber(),
                receipt.getInstrumentNumber(),
                receipt.getInstrumentDate(),
                receipt.getOtherReferenceNumber(),
                refBankAccountEntry,
                refIssuingBankAccountEntry,
                receipt.getPosReferenceNumber()
        );
    }
}
