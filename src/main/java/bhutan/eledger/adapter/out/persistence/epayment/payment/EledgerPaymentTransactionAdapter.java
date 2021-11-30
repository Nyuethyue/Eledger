package bhutan.eledger.adapter.out.persistence.epayment.payment;

import bhutan.eledger.application.port.in.eledger.transaction.CreateTransactionsUseCase;
import bhutan.eledger.application.port.out.epayment.payment.EledgerPaymentTransactionPort;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
class EledgerPaymentTransactionAdapter implements EledgerPaymentTransactionPort {
    private final CreateTransactionsUseCase createTransactionsUseCase;

    @Override
    public void create(Receipt receipt) {

        var transactionsCreationCommand = new CreateTransactionsUseCase.CreateTransactionsCommand(
                receipt.getReceiptNumber(),
                new CreateTransactionsUseCase.TaxpayerCommand(
                        receipt.getTaxpayer().getTpn(),
                        receipt.getTaxpayer().getName()
                ),
                receipt.getPayments()
                        .stream()
                        .map(p ->
                                new CreateTransactionsUseCase.TransactionCommand(
                                        p.getGlAccount().getCode(),
                                        receipt.getCreationDateTime().toLocalDate(),
                                        p.getPaidAmount(),
                                        "PAYMENT",
                                        Set.of(
                                                new CreateTransactionsUseCase.TransactionAttributeCommand(
                                                        "TARGET_DRN",
                                                        receipt.getDrn()
                                                ),
                                                new CreateTransactionsUseCase.TransactionAttributeCommand(
                                                        "TARGET_TRANSACTION_ID",
                                                        p.getElTargetTransactionId().toString()
                                                )
                                        )
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );

        createTransactionsUseCase.create(transactionsCreationCommand);
    }
}
