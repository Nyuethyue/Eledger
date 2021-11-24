package bhutan.eledger.application.service.epayment.generatereceipt;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.epayment.generatereceipt.GenerateCashReceiptUseCase;
import bhutan.eledger.application.port.out.epayment.generatereceipt.CashReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.generatereceipt.EledgerPaymentTransactionPort;
import bhutan.eledger.application.port.out.epayment.generatereceipt.ReceiptNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.application.port.out.epayment.taxpayer.EpTaxpayerRepositoryPort;
import bhutan.eledger.domain.epayment.generatereceipt.*;
import bhutan.eledger.domain.epayment.paymentadvice.PayableLine;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class GenerateCashReceiptService implements GenerateCashReceiptUseCase {
    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;
    private final ReceiptNumberGeneratorPort receiptNumberGeneratorPort;
    private final CashReceiptRepositoryPort cashReceiptRepositoryPort;
    private final EpTaxpayerRepositoryPort epTaxpayerRepositoryPort;
    private final EledgerPaymentTransactionPort eledgerPaymentTransactionPort;

    @Override
    public Receipt generate(GenerateCashReceiptCommand command) {
        log.trace("Generating cash receipt by command: {}", command);

        PaymentAdvice updatedPaymentAdvice = updatePaymentAdvice(command);

        LocalDateTime creationDateTime = LocalDateTime.now();

        String receiptNumber = receiptNumberGeneratorPort.generate(creationDateTime.toLocalDate());

        log.trace("Receipt number: [{}], generated in: {}", receiptNumber, creationDateTime.toLocalDate());

        var payments =
                resolvePayments(command, updatedPaymentAdvice);

        CashReceipt cashReceipt = CashReceipt.withoutId(
                updatedPaymentAdvice.getDrn(),
                PaymentMode.CASH,
                updatedPaymentAdvice.isPaid() ? ReceiptStatus.PAID : ReceiptStatus.SPLIT_PAYMENT,
                command.getCurrency(),
                receiptNumber,
                creationDateTime,
                epTaxpayerRepositoryPort.requiredReadByTpn(updatedPaymentAdvice.getTaxpayer().getTpn()),
                payments
        );

        log.trace("Persisting cash receipt: {}", cashReceipt);

        CashReceipt persistedCashReceipt = cashReceiptRepositoryPort.create(cashReceipt);

        log.debug("Cash receipt with id: {} successfully created.", persistedCashReceipt.getId());

        log.trace("Creating eledger payment transaction: {}", cashReceipt);

        eledgerPaymentTransactionPort.create(cashReceipt);

        return persistedCashReceipt;
    }

    private PaymentAdvice updatePaymentAdvice(GenerateCashReceiptCommand command) {

        PaymentAdvice paymentAdvice = paymentAdviceRepositoryPort.requiredReadById(command.getPaymentAdviceId());

        command.getPayments()
                .forEach(pc -> {
                    PayableLine payableLine = paymentAdvice.getRequiredPayableLineById(pc.getPayableLineId());

                    if (!payableLine.getAmount().equals(pc.getPaidAmount())) {
                        throw new ViolationException(new ValidationError().addViolation("paidAmount", "Paid amount must be equal to be paid amount."));
                    }

                    payableLine.pay(pc.getPaidAmount());
                });

        if (paymentAdvice.isPaid()) {
            paymentAdvice.changeStatus(PaymentAdviceStatus.PAID);
        } else {
            paymentAdvice.changeStatus(PaymentAdviceStatus.SPLIT_PAYMENT);
        }

        log.trace("Updating payment advice: {}", paymentAdvice);

        paymentAdviceRepositoryPort.update(paymentAdvice);

        return paymentAdvice;
    }

    private Collection<Payment> resolvePayments(GenerateCashReceiptCommand command, PaymentAdvice paymentAdvice) {
        return command.getPayments()
                .stream()
                .map(pc -> {

                    PayableLine payableLine = paymentAdvice.getRequiredPayableLineById(pc.getPayableLineId());

                    return Payment.withoutId(
                            payableLine.getGlAccount(),
                            pc.getPaidAmount(),
                            payableLine.getId(),
                            payableLine.getElTransactionId()
                    );
                })
                .collect(Collectors.toUnmodifiableList());
    }
}
