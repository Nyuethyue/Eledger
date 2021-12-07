package bhutan.eledger.application.port.in.epayment.paymentadvice;

import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface CreatePaymentAdviceUseCase {

    Long create(@Valid UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand command);

}
