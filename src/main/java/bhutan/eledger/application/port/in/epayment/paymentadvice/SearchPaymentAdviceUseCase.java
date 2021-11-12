package bhutan.eledger.application.port.in.epayment.paymentadvice;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import bhutan.eledger.domain.epayment.PaymentAdvice;
import am.iunetworks.lib.common.persistence.search.SearchResult;

public interface SearchPaymentAdviceUseCase {
    SearchResult<PaymentAdvice> search(SearchPaymentAdviseCommand command);

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
