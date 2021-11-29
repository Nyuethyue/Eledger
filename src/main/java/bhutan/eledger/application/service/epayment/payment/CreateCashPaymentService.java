package bhutan.eledger.application.service.epayment.payment;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.epayment.payment.CreateCashPaymentUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreatePaymentCommonCommand;
import bhutan.eledger.application.port.out.epayment.payment.CashReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.payment.EledgerPaymentTransactionPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.application.port.out.epayment.taxpayer.EpTaxpayerRepositoryPort;
import bhutan.eledger.domain.epayment.payment.*;
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
class CreateCashPaymentService implements CreateCashPaymentUseCase {
    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;
    private final ReceiptNumberGeneratorPort receiptNumberGeneratorPort;
    private final CashReceiptRepositoryPort cashReceiptRepositoryPort;
    private final EpTaxpayerRepositoryPort epTaxpayerRepositoryPort;
    private final EledgerPaymentTransactionPort eledgerPaymentTransactionPort;

    @Override
    public Receipt create(CreateCashPaymentCommand command) {
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

    private PaymentAdvice updatePaymentAdvice(CreateCashPaymentCommand command) {

        PaymentAdvice paymentAdvice = paymentAdviceRepositoryPort.requiredReadById(command.getPaymentAdviceId());

        checkStatus(paymentAdvice);

        command.getPayments()
                .forEach(pc -> {
                    PayableLine payableLine = paymentAdvice.getRequiredPayableLineById(pc.getPayableLineId());

                    checkPayableLine(payableLine, pc);


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

    private Collection<Payment> resolvePayments(CreateCashPaymentCommand command, PaymentAdvice paymentAdvice) {
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

    private void checkPayableLine(PayableLine payableLine, CreatePaymentCommonCommand.PaymentCommand paymentCommand) {
        if (payableLine.isPaid()) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "payments.payableLineId",
                                    "The payable line by GL code: [" + payableLine.getGlAccount().getCode() + "] has been already payed."
                            )
            );
        }

        if (payableLine.getAmountToBePaid().compareTo(paymentCommand.getPaidAmount()) != 0) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "paidAmount",
                                    "Paid amount must be equal to be paid amount. Amount to be paid: " + payableLine.getAmountToBePaid() + ", Paid amount: " + paymentCommand.getPaidAmount()
                            )
            );
        }
    }

    private void checkStatus(PaymentAdvice paymentAdvice) {
        if (paymentAdvice.isPaid()) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "paymentAdviceId",
                                    "Payment advice by pan: [" + paymentAdvice.getPan() + "] has been already paid."
                            )
            );
        }
    }
}
