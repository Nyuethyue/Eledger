package bhutan.eledger.application.port.in.epayment.paymentadvice;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;

@Validated
public interface CreatePaymentAdvicesUseCase {

    void create(@Valid CreatePaymentAdvicesCommand command);

    @Data
    class CreatePaymentAdvicesCommand {
        private final Collection<CreatePaymentAdviceUseCase.CreatePaymentAdviceCommand> createPaymentAdvices;
    }

}
