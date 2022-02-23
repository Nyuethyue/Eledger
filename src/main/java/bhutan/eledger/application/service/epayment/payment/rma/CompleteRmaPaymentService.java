package bhutan.eledger.application.service.epayment.payment.rma;

import bhutan.eledger.application.port.in.epayment.payment.rma.CompleteRmaPaymentUseCase;
import bhutan.eledger.application.port.out.epayment.eledger.CreateEledgerTransactionPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntryRepository;
import bhutan.eledger.common.ref.refentry.RefName;
import bhutan.eledger.domain.epayment.payment.*;
import bhutan.eledger.domain.epayment.paymentadvice.PayableLine;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CompleteRmaPaymentService implements CompleteRmaPaymentUseCase {

    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;
    private final ReceiptNumberGeneratorPort receiptNumberGeneratorPort;
    private final CreateEledgerTransactionPort eledgerPaymentTransactionPort;
    private final ReceiptRepositoryPort receiptRepositoryPort;
    private final RefEntryRepository refEntryRepository;

    @Override
    public void complete(CompleteRmaPaymentCommand command) {
        PaymentAdvice paymentAdvice = paymentAdviceRepositoryPort.requiredReadById(command.getPaymentAdviceId());

        var payments = paymentAdvice.getPayableLines()
                .stream()
                .filter(PayableLine::isNotPaid)
                .map(pl -> Payment.withoutId(
                        pl.getGlAccount(),
                        pl.getToBePaidAmount(),
                        pl.getId(),
                        pl.getElTransactionId(),
                        PaymentPaInfo.withoutId(
                                paymentAdvice.getId(),
                                paymentAdvice.getPan(),
                                paymentAdvice.getDrn()
                        )
                ))
                .toList();

        paymentAdvice.pay(command.getPaidAmount());

        paymentAdviceRepositoryPort.update(paymentAdvice);

        LocalDateTime creationDateTime = LocalDateTime.now();

        String receiptNumber = receiptNumberGeneratorPort.generate(creationDateTime.toLocalDate());

        log.trace("Receipt number: [{}], generated in: {}", receiptNumber, creationDateTime.toLocalDate());

        var refCurrencyEntry = refEntryRepository.findByRefNameAndCode(
                RefName.CURRENCY.getValue(),
                "BTN"
        );

        var receipt = Receipt.rmaWithoutId(
                PaymentMode.RMA,
                ReceiptStatus.PRE_RECONCILIATION,
                refCurrencyEntry,
                receiptNumber,
                creationDateTime,
                paymentAdvice.getTaxpayer(),
                payments,
                command.getPaidAmount(),
                null
        );

        log.trace("Persisting rma receipt: {}", receipt);

        Receipt persistedReceipt = receiptRepositoryPort.create(receipt);

        log.debug("Rma receipt with id: {} successfully created.", persistedReceipt.getId());

        log.trace("Creating eledger payment transaction: {}", receipt);

        eledgerPaymentTransactionPort.create(receipt);

    }
}
