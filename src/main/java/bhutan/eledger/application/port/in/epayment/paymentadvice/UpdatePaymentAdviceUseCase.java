package bhutan.eledger.application.port.in.epayment.paymentadvice;

import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface UpdatePaymentAdviceUseCase {

    void update(@NotNull Long id, @Valid UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand command);

    void update(@NotNull PaymentAdvice paymentAdvice, @Valid UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand command);

}
