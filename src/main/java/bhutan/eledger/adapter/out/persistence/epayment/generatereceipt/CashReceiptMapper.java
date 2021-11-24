package bhutan.eledger.adapter.out.persistence.epayment.generatereceipt;

import bhutan.eledger.domain.epayment.generatereceipt.CashReceipt;
import bhutan.eledger.domain.epayment.generatereceipt.Payment;
import bhutan.eledger.domain.epayment.generatereceipt.PaymentMode;
import bhutan.eledger.domain.epayment.generatereceipt.ReceiptStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CashReceiptMapper {

    ReceiptEntity mapToEntity(CashReceipt cashReceipt) {
        ReceiptEntity receiptEntity = new ReceiptEntity(
                cashReceipt.getId(),
                cashReceipt.getDrn(),
                cashReceipt.getPaymentMode().getValue(),
                cashReceipt.getStatus().getValue(),
                cashReceipt.getCurrency(),
                cashReceipt.getReceiptNumber(),
                null,
                cashReceipt.getCreationDateTime(),
                cashReceipt.getTaxpayer()
        );

        receiptEntity.setPayments(
                cashReceipt.getPayments()
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

    CashReceipt mapToDomain(ReceiptEntity receipt) {
        return CashReceipt.withId(
                receipt.getId(),
                receipt.getDrn(),
                PaymentMode.of(receipt.getPaymentMode()),
                ReceiptStatus.of(receipt.getStatus()),
                receipt.getCurrency(),
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
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
