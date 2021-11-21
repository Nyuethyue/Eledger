package bhutan.eledger.application.port.in.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.PaymentAdvice;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
        @NotNull
        @NotEmpty
        private final String taxpayerType;
        private final String tpn;
        private final String pan;
    }
}
