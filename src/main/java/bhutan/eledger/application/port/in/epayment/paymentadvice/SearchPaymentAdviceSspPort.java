package bhutan.eledger.application.port.in.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface SearchPaymentAdviceSspPort {

    SearchResult<PaymentAdvice> search(PaymentAdviseSspSearchCommand command);

    @Getter
    @ToString
    class PaymentAdviseSspSearchCommand extends AbstractSearchCommand {

        private final String tpn;
        private final String pan;
        private final PaymentAdviceStatus status;
        private final LocalDateTime creationDateTimeFrom;
        private final LocalDateTime creationDateTimeTo;
        private final BigDecimal totalToBePaidAmountFrom;
        private final BigDecimal totalToBePaidAmountTo;


        public PaymentAdviseSspSearchCommand(int page,
                                             int size,
                                             String sortProperty,
                                             String sortDirection,
                                             String tpn,
                                             String pan, PaymentAdviceStatus status, LocalDateTime creationDateTimeFrom, LocalDateTime creationDateTimeTo, BigDecimal totalToBePaidAmountFrom, BigDecimal totalToBePaidAmountTo) {
            super(page, size, sortProperty, sortDirection);
            this.tpn = tpn;
            this.pan = pan;
            this.status = status;
            this.creationDateTimeFrom = creationDateTimeFrom;
            this.creationDateTimeTo = creationDateTimeTo;
            this.totalToBePaidAmountFrom = totalToBePaidAmountFrom;
            this.totalToBePaidAmountTo = totalToBePaidAmountTo;
        }
    }
}
