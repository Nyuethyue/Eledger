package bhutan.eledger.application.port.in.epayment.payment.rma;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface InitiateRmaPaymentUseCase {

    void initiate(@Valid InitiateRmaPaymentCommand command);

    @Data
    class InitiateRmaPaymentCommand {
        @NotNull
        private final String orderNo;

        @JsonCreator
        public InitiateRmaPaymentCommand(String orderNo) {
            this.orderNo = orderNo;
        }
    }
}
