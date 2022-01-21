package bhutan.eledger.application.port.in.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.paymentadvice.FlatPaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@Validated
public interface SearchPaymentAdviceUseCase {
    SearchResult<PaymentAdvice> search(@Valid SearchPaymentAdviseCommand command);

    @Data
    class SearchPaymentAdviseCommand {
        @PositiveOrZero
        private final Integer page;
        @Positive
        private final Integer size;
        private final String sortProperty;
        private final String sortDirection;

        private final String tpn;
        private final String pan;
    }

    Collection<FlatPaymentAdvice> searchByDrns(@Valid SearchPaymentAdviceUseCase.SearchPaymentAdviceByDrnCommand command);

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
