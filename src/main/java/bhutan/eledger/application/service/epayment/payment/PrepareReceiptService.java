package bhutan.eledger.application.service.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreatePaymentCommonCommand;
import bhutan.eledger.application.port.in.epayment.payment.CreatePaymentsCommonCommand;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.payment.Payment;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class PrepareReceiptService {
    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;
    private final PaymentAdviceOnPaymentUpdaterService paymentAdviceOnPaymentUpdaterService;
    private final PaymentsResolverService paymentsResolverService;

    /**
     * Updates payment advices, resolves payments of payment lines then returns receipt creation needed context
     *
     * @param command payment creation command
     *
     * @return receipt creation needed context
     */
    ReceiptCreationContext prepare(CreatePaymentsCommonCommand<?> command) {

        Collection<Payment> payments = new HashSet<>();

        List<PaymentAdvice> updatedPaymentAdvices = new ArrayList<>();

        boolean isAllPaid = true;

        var idToPaymentAdvice = paymentAdviceRepositoryPort.requiredReadAllByIds(
                        command.getReceipts()
                                .stream()
                                .map(CreatePaymentCommonCommand::getPaymentAdviceId)
                                .collect(Collectors.toList())
                )
                .stream()
                .collect(Collectors.toMap(PaymentAdvice::getId, Function.identity()));

        for (CreatePaymentCommonCommand paymentCommand : command.getReceipts()) {
            var updatedPaymentAdvice = paymentAdviceOnPaymentUpdaterService.updatePaymentAdvice(paymentCommand, idToPaymentAdvice.get(paymentCommand.getPaymentAdviceId()));
            updatedPaymentAdvices.add(updatedPaymentAdvice);

            payments.addAll(paymentsResolverService.resolvePayments(paymentCommand, updatedPaymentAdvice));

            isAllPaid = isAllPaid && updatedPaymentAdvice.isPaid();
        }

        return new ReceiptCreationContext(idToPaymentAdvice, updatedPaymentAdvices, isAllPaid, payments);
    }
}
