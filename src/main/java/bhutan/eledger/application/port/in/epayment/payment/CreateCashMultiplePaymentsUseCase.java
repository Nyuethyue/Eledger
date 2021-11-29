package bhutan.eledger.application.port.in.epayment.payment;

import bhutan.eledger.domain.epayment.payment.Receipt;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface CreateCashMultiplePaymentsUseCase {

    Collection<Receipt> create(@Valid CreateCashMultiplePaymentsCommand command);

    @Getter
    @ToString
    class CreateCashMultiplePaymentsCommand {
        @Valid
        @NotNull
        @NotEmpty
        private final Collection<CreateCashPaymentUseCase.CreateCashPaymentCommand> receipts;

        @JsonCreator
        public CreateCashMultiplePaymentsCommand(Collection<CreateCashPaymentUseCase.CreateCashPaymentCommand> receipts) {
            this.receipts = receipts;
        }
    }
}
