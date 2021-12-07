package bhutan.eledger.application.service.epayment.paymentadvice;

import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpdatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdviceUseCase;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpsertPaymentAdviceService implements UpsertPaymentAdviceUseCase {
    private static final Collection<PaymentAdviceStatus> UPDATABLE_STATUSES =
            Stream.of(
                            PaymentAdviceStatus.values()
                    )
                    .filter(s -> !PaymentAdviceStatus.RECONCILED.equals(s))
                    .collect(Collectors.toUnmodifiableSet());

    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;
    private final CreatePaymentAdviceUseCase createPaymentAdviceUseCase;
    private final UpdatePaymentAdviceUseCase updatePaymentAdviceUseCase;

    @Override
    public void upsert(UpsertPaymentAdviceCommand command) {
        paymentAdviceRepositoryPort.readByDrnAndStatusIn(command.getDrn(), UPDATABLE_STATUSES)
                .ifPresentOrElse(
                        paymentAdvice ->
                                updatePaymentAdviceUseCase.update(paymentAdvice, command),
                        () -> createPaymentAdviceUseCase.create(command)
                );
    }
}
