package bhutan.eledger.application.service.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreateCashMultiplePaymentsUseCase;
import bhutan.eledger.application.port.out.epayment.eledger.CreateEledgerTransactionPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.payment.ReceiptRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntryRepository;
import bhutan.eledger.common.ref.refentry.RefName;
import bhutan.eledger.domain.epayment.payment.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateCashMultiplePaymentsService implements CreateCashMultiplePaymentsUseCase {
    private final RefEntryRepository refEntryRepository;
    private final ReceiptNumberGeneratorPort receiptNumberGeneratorPort;
    private final CreateEledgerTransactionPort eledgerPaymentTransactionPort;
    private final ReceiptRepositoryPort receiptRepositoryPort;
    private final PrepareReceiptService prepareReceiptService;

    @Override
    public Receipt create(CreateCashMultiplePaymentsCommand command) {

        var receiptCreationContext = prepareReceiptService.prepare(command);

        var refCurrencyEntry = refEntryRepository.findByRefNameAndId(
                RefName.CURRENCY.getValue(),
                command.getRefCurrencyId()
        );

        LocalDateTime creationDateTime = LocalDateTime.now();

        String receiptNumber = receiptNumberGeneratorPort.generate(creationDateTime.toLocalDate());

        log.trace("Receipt number: [{}], generated in: {}", receiptNumber, creationDateTime.toLocalDate());

        var receipt = Receipt.cashWithoutId(
                PaymentMode.CASH,
                receiptCreationContext.isAllPaid() ? ReceiptStatus.PAID : ReceiptStatus.SPLIT_PAYMENT,
                refCurrencyEntry,
                receiptNumber,
                creationDateTime,
                receiptCreationContext.getAnyPa().getTaxpayer(),
                receiptCreationContext.getPayments(),
                receiptCreationContext.getPayments().stream()
                        .map(Payment::getPaidAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                null
        );

        log.trace("Persisting cash receipt: {}", receipt);

        Receipt persistedCashReceipt = receiptRepositoryPort.create(receipt);

        log.debug("Cash receipt with id: {} successfully created.", persistedCashReceipt.getId());

        log.trace("Creating eledger payment transaction: {}", receipt);

        eledgerPaymentTransactionPort.create(receipt);

        return persistedCashReceipt;

    }
}
