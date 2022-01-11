package bhutan.eledger.application.service.epayment.payment;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.epayment.payment.CreatePaymentCommonCommand;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.paymentadvice.PayableLine;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
class PaymentAdviceOnPaymentUpdaterService {
    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;

    PaymentAdvice updatePaymentAdvice(CreatePaymentCommonCommand command) {

        PaymentAdvice paymentAdvice = paymentAdviceRepositoryPort.requiredReadById(command.getPaymentAdviceId());

        return updatePaymentAdvice(command, paymentAdvice);
    }

    PaymentAdvice updatePaymentAdvice(CreatePaymentCommonCommand command, PaymentAdvice paymentAdvice) {

        checkStatus(paymentAdvice);

        command.getPayableLines()
                .forEach(plc -> {
                    PayableLine payableLine = paymentAdvice.getRequiredPayableLineById(plc.getPayableLineId());

                    checkPayableLine(payableLine, plc);

                    payableLine.pay(plc.getPaidAmount());
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

    private void checkPayableLine(PayableLine payableLine, CreatePaymentCommonCommand.PayableLineCommand payableLineCommand) {
        if (payableLine.isPaid()) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "payments.payableLineId",
                                    "The payable line by GL code: [" + payableLine.getGlAccount().getCode() + "] has been already payed."
                            )
            );
        }

        if (payableLine.getAmountToBePaid().compareTo(payableLineCommand.getPaidAmount()) != 0) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "paidAmount",
                                    "Paid amount must be equal to be paid amount. Amount to be paid: " + payableLine.getAmountToBePaid() + ", Paid amount: " + payableLineCommand.getPaidAmount()
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
