package bhutan.eledger.application.port.in.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.search.AbstractSearchCommand;
import am.iunetworks.lib.common.persistence.search.SearchResult;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import lombok.Getter;
import lombok.ToString;

public interface SearchPaymentAdvicePort {

    SearchResult<PaymentAdvice> search(PaymentAdviseSearchCommand command);

    @Getter
    @ToString
    class PaymentAdviseSearchCommand extends AbstractSearchCommand {

        private final String taxpayerType;
        private final String tpn;
        private final String pan;

        public PaymentAdviseSearchCommand(int page,
                                          int size,
                                          String sortProperty,
                                          String sortDirection,
                                          String taxpayerType,
                                          String tpn,
                                          String pan) {
            super(page, size, sortProperty, sortDirection);
            this.taxpayerType = taxpayerType;
            this.tpn = tpn;
            this.pan = pan;
        }
    }
}
