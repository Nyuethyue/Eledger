package bhutan.eledger.adapter.out.eledger.epayment;

import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdvicesUseCase;
import bhutan.eledger.application.port.out.eledger.epayment.UpsertEpaymentPaymentAdvicePort;
import bhutan.eledger.domain.eledger.accounting.PaymentAdviceData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class UpsertEpaymentPaymentAdviceAdapter implements UpsertEpaymentPaymentAdvicePort {
    private final UpsertPaymentAdvicesUseCase createPaymentAdvicesUseCase;

    @Override
    public void upsert(Collection<PaymentAdviceData> paymentAdviceDatas) {
        createPaymentAdvicesUseCase.upsert(
                new UpsertPaymentAdvicesUseCase.UpsertPaymentAdvicesCommand(
                        paymentAdviceDatas
                                .stream()
                                .map(pad ->
                                        new UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand(
                                                pad.getDrn(),
                                                new UpsertPaymentAdviceUseCase.TaxpayerCommand(
                                                        pad.getTaxpayer().getTpn(),
                                                        pad.getTaxpayer().getName()
                                                ),
                                                pad.getDueDate(),
                                                new UpsertPaymentAdviceUseCase.PeriodCommand(
                                                        pad.getPeriod().getYear(),
                                                        pad.getPeriod().getSegment()
                                                ),
                                                pad.getPayableLines()
                                                        .stream()
                                                        .map(pl ->
                                                                new UpsertPaymentAdviceUseCase.PayableLineCommand(
                                                                        pl.getAmount(),
                                                                        new UpsertPaymentAdviceUseCase.GLAccountCommand(
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
