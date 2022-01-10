package bhutan.eledger.application.service.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreateChequePaymentsUseCase;
import bhutan.eledger.application.port.out.epayment.eledger.CreateEledgerTransactionPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntryRepository;
import bhutan.eledger.common.ref.refentry.RefName;
import bhutan.eledger.domain.epayment.payment.Payment;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
import bhutan.eledger.domain.epayment.payment.Receipt;
import bhutan.eledger.domain.epayment.payment.ReceiptStatus;
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
class CreateChequePaymentsService implements CreateChequePaymentsUseCase {
    private final RefEntryRepository refEntryRepository;
    private final ReceiptNumberGeneratorPort receiptNumberGeneratorPort;
    private final CreateEledgerTransactionPort eledgerPaymentTransactionPort;
    private final ReceiptRepositoryPort receiptRepositoryPort;
    private final PrepareReceiptService prepareReceiptService;

    @Override
    public Receipt create(CreateChequePaymentsCommand command) {

        var receiptCreationContext = prepareReceiptService.prepare(command);

        LocalDateTime creationDateTime = LocalDateTime.now();

        String receiptNumber = receiptNumberGeneratorPort.generate(creationDateTime.toLocalDate());

        log.trace("Receipt number: [{}], generated in: {}", receiptNumber, creationDateTime.toLocalDate());

        var refCurrencyEntry = refEntryRepository.findByRefNameAndId(
                RefName.CURRENCY.getValue(),
                command.getRefCurrencyId()
        );

        var refBankBranchEntry = refEntryRepository.findByRefNameAndId(
                RefName.BANK_BRANCH.getValue(),
                command.getBankBranchId()
        );


        var receipt = Receipt.chequeWithoutId(
                PaymentMode.CHEQUE,
                receiptCreationContext.isAllPaid() ? ReceiptStatus.PAID : ReceiptStatus.SPLIT_PAYMENT,
                refCurrencyEntry,
                receiptNumber,
                creationDateTime,
                receiptCreationContext.getAnyPa().getTaxpayer(),
                receiptCreationContext.getPayments(),
                receiptCreationContext.getTotalPaidAmount(),
                null,
                command.getInstrumentNumber(),
                command.getInstrumentDate(),
                command.getOtherReferenceNumber(),
                refBankBranchEntry
        );

        log.trace("Persisting cheque receipt: {}", receipt);

        Receipt persistedCashReceipt = receiptRepositoryPort.create(receipt);

        log.debug("Cheque receipt with id: {} successfully created.", persistedCashReceipt.getId());

        log.trace("Creating eledger payment transaction: {}", receipt);

        eledgerPaymentTransactionPort.create(receipt);

        return persistedCashReceipt;

    }
}
