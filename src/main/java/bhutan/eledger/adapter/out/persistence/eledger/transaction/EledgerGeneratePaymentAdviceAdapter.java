package bhutan.eledger.adapter.out.persistence.eledger.transaction;

import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdvicesUseCase;
import bhutan.eledger.application.port.out.eledger.transaction.EledgerGeneratePaymentAdvicePort;
import bhutan.eledger.domain.eledger.transaction.PaymentAdviceData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
//todo move po another package
class EledgerGeneratePaymentAdviceAdapter implements EledgerGeneratePaymentAdvicePort {
    private final CreatePaymentAdvicesUseCase createPaymentAdvicesUseCase;

    @Override
    public void generate(Collection<PaymentAdviceData> paymentAdviceDatas) {
        createPaymentAdvicesUseCase.create(
                new CreatePaymentAdvicesUseCase.CreatePaymentAdvicesCommand(
                        paymentAdviceDatas
                                .stream()
                                .map(pad ->
                                        new CreatePaymentAdviceUseCase.CreatePaymentAdviceCommand(
                                                pad.getDrn(),
                                                new CreatePaymentAdviceUseCase.TaxpayerCommand(
                                                        pad.getTaxpayer().getTpn(),
                                                        pad.getTaxpayer().getName()
                                                ),
                                                pad.getDueDate(),
                                                new CreatePaymentAdviceUseCase.PeriodCommand(
                                                        pad.getPeriod().getYear(),
                                                        pad.getPeriod().getSegment()
                                                ),
                                                pad.getPayableLines()
                                                        .stream()
                                                        .map(pl ->
                                                                new CreatePaymentAdviceUseCase.PayableLineCommand(
                                                                        pl.getAmount(),
                                                                        new CreatePaymentAdviceUseCase.GLAccountCommand(
                                                                                pl.getGlAccount().getCode(),
                                                                                pl.getGlAccount().getDescriptions()
                                                                        ),
                                                                        pl.getTransactionId()
                                                                )
                                                        )
                                                        .collect(Collectors.toList())

                                        )
                                )
                                .collect(Collectors.toList())
                )
        );
    }
}
