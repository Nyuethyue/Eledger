package bhutan.eledger.application.service.epayment.paymentadvice;

import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdvicesUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreatePaymentAdvicesService implements CreatePaymentAdvicesUseCase {
    private final CreatePaymentAdviceUseCase createPaymentAdviceUseCase;

    @Override
    public void create(CreatePaymentAdvicesCommand command) {
        command.getCreatePaymentAdvices()
                .forEach(createPaymentAdviceUseCase::create);
    }
}
