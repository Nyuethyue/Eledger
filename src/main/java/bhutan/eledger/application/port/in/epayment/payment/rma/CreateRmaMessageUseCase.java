package bhutan.eledger.application.port.in.epayment.payment.rma;

import bhutan.eledger.domain.epayment.rma.RmaMessage;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CreateRmaMessageUseCase {

    RmaMessage create(@Valid CreateRmaMessageCommand command);

    @Data
    class CreateRmaMessageCommand {
        @NotNull
        private final Long paymentAdviceId;

        @JsonCreator
        public CreateRmaMessageCommand(Long paymentAdviceId) {
            this.paymentAdviceId = paymentAdviceId;
        }
    }
}
