package bhutan.eledger.application.port.in.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.search.SearchResult;
import am.iunetworks.lib.common.validation.constraints.InList;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Validated
public interface SearchPaymentAdviceSspUseCase {
    SearchResult<PaymentAdvice> search(@Valid SearchPaymentAdviseSspCommand command);

    @Data
    class SearchPaymentAdviseSspCommand {
        @PositiveOrZero
        private final Integer page;
        @Positive
        private final Integer size;
        private final String sortProperty;
        private final String sortDirection;

        @NotNull
        private final String tpn;
        private final String pan;
        @InList(values = {"INITIAL", "SPLIT_PAYMENT", "PAID", "PRE_RECONCILED", "RECONCILED"})
        private final String status;
        private final LocalDateTime creationDateTimeFrom;
        private final LocalDateTime creationDateTimeTo;
        private final BigDecimal totalToBePaidAmountFrom;
        private final BigDecimal totalToBePaidAmountTo;

        public PaymentAdviceStatus getStatus() {
            return PaymentAdviceStatus.of(status);
        }
    }
}
