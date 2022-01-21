package bhutan.eledger.application.port.in.epayment.paymentadvice;

import bhutan.eledger.domain.epayment.paymentadvice.FlatPaymentAdvice;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface SearchFlatPaymentAdviceByDrnsUseCase {
    Collection<FlatPaymentAdvice> searchByDrns(@Valid SearchPaymentAdviceByDrnCommand command);

    @Data
    @NoArgsConstructor
    class SearchPaymentAdviceByDrnCommand {
        private Collection<DrnCommand> drnCommands;
    }

    @Data
    class DrnCommand {
        @NotNull
        private final String drn;

        @JsonCreator
        public DrnCommand(String drn) {
            this.drn = drn;
        }
    }
}
