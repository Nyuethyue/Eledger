package bhutan.eledger.application.port.in.epayment.paymentadvice;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;

@Validated
public interface UpsertPaymentAdvicesUseCase {

    void upsert(@Valid UpsertPaymentAdvicesCommand command);

    @Data
    class UpsertPaymentAdvicesCommand {
        private final Collection<UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand> createPaymentAdvices;
    }

}
