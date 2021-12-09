package bhutan.eledger.application.service.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreateChequePaymentUseCase;
import bhutan.eledger.application.port.out.epayment.eledger.CreateEledgerTransactionPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.taxpayer.EpTaxpayerRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntryRepository;
import bhutan.eledger.common.ref.refentry.RefName;
import bhutan.eledger.domain.epayment.payment.Payment;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
import bhutan.eledger.domain.epayment.payment.Receipt;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateChequePaymentService implements CreateChequePaymentUseCase {
    private final ReceiptNumberGeneratorPort receiptNumberGeneratorPort;
    private final ReceiptRepositoryPort receiptRepositoryPort;
    private final EpTaxpayerRepositoryPort epTaxpayerRepositoryPort;
    private final CreateEledgerTransactionPort eledgerPaymentTransactionPort;
    private final RefEntryRepository refEntryRepository;
    private final PaymentAdviceOnPaymentUpdaterService paymentAdviceOnPaymentUpdaterService;
    private final PaymentsResolverService paymentsResolverService;

    @Override
    public Receipt create(CreateChequePaymentCommand command) {
        log.trace("Generating cash receipt by command: {}", command);

        PaymentAdvice updatedPaymentAdvice = paymentAdviceOnPaymentUpdaterService.updatePaymentAdvice(command);

        LocalDateTime creationDateTime = LocalDateTime.now();

        String receiptNumber = receiptNumberGeneratorPort.generate(creationDateTime.toLocalDate());

        log.trace("Receipt number: [{}], generated in: {}", receiptNumber, creationDateTime.toLocalDate());

        var payments =
                paymentsResolverService.resolvePayments(command, updatedPaymentAdvice);

        var refCurrencyEntry = refEntryRepository.findByRefNameAndId(
                RefName.CURRENCY.getValue(),
                command.getRefCurrencyId()
        );

        var refBankBranchEntry = refEntryRepository.findByRefNameAndId(
                RefName.BANK_BRANCH.getValue(),
                command.getBankBranchId()
        );

        //todo check is ref open


        Receipt receipt = Receipt.chequeWithoutId(
                updatedPaymentAdvice.getDrn(),
                PaymentMode.CHEQUE,
                updatedPaymentAdvice.isPaid() ? ReceiptStatus.PAID : ReceiptStatus.SPLIT_PAYMENT,
                refCurrencyEntry,
                receiptNumber,
                creationDateTime,
                epTaxpayerRepositoryPort.requiredReadByTpn(updatedPaymentAdvice.getTaxpayer().getTpn()),
                payments,
                payments.stream()
                        .map(Payment::getPaidAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                null,// todo apply security number,
                command.getInstrumentNumber(),
                command.getInstrumentDate(),
                command.getOtherReferenceNumber(),
                refBankBranchEntry,
                updatedPaymentAdvice.getPan()
        );

        log.trace("Persisting cheque receipt: {}", receipt);

        Receipt persistedReceipt = receiptRepositoryPort.create(receipt);

        log.debug("Cheque receipt with id: {} successfully created.", persistedReceipt.getId());

        log.trace("Creating eledger payment transaction: {}", receipt);

        eledgerPaymentTransactionPort.create(receipt);

        return persistedReceipt;
    }
}
