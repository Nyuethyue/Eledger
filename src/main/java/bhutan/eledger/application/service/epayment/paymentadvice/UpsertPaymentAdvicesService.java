package bhutan.eledger.application.service.epayment.paymentadvice;

import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdvicesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class UpsertPaymentAdvicesService implements UpsertPaymentAdvicesUseCase {
    private final UpsertPaymentAdviceUseCase upsertPaymentAdviceUseCase;

    @Override
    public void upsert(UpsertPaymentAdvicesCommand command) {
        command.getCreatePaymentAdvices()
                .forEach(upsertPaymentAdviceUseCase::upsert);
    }
}
