package bhutan.eledger.application.service.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreateCashWarrantPaymentsUseCase;
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
public class CreateCashWarrantPaymentsService implements CreateCashWarrantPaymentsUseCase {
    private final RefEntryRepository refEntryRepository;
    private final ReceiptNumberGeneratorPort receiptNumberGeneratorPort;
    private final PrepareReceiptService prepareReceiptService;
    private final ReceiptRepositoryPort receiptRepositoryPort;
    private final CreateEledgerTransactionPort eledgerPaymentTransactionPort;

    @Override
    public Receipt create(CreateCashWarrantPaymentsCommand command) {

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
                command.getPayableBankBranchId()
        );

        var refIssuingBankBranchEntry = refEntryRepository.findByRefNameAndId(
                RefName.BANK_BRANCH.getValue(),
                command.getIssuingBankBranchId()
        );

        var receipt = Receipt.cashWarrantWithoutId(
                PaymentMode.CASH_WARRANT,
                receiptCreationContext.isAllPaid() ? ReceiptStatus.PAID : ReceiptStatus.SPLIT_PAYMENT,
                refCurrencyEntry,
                receiptNumber,
                creationDateTime,
                receiptCreationContext.getAnyPa().getTaxpayer(),
                receiptCreationContext.getPayments(),
                receiptCreationContext.getPayments().stream()
                        .map(Payment::getPaidAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                null,
                command.getInstrumentNumber(),
                command.getInstrumentDate(),
                command.getOtherReferenceNumber(),
                refBankBranchEntry,
                refIssuingBankBranchEntry
        );

        log.trace("Persisting cash warrant receipt: {}", receipt);

        Receipt persistedCashReceipt = receiptRepositoryPort.create(receipt);

        log.debug("Cash warrant receipt with id: {} successfully created.", persistedCashReceipt.getId());

        log.trace("Creating eledger payment transaction: {}", receipt);

        eledgerPaymentTransactionPort.create(receipt);

        return persistedCashReceipt;


    }
}
