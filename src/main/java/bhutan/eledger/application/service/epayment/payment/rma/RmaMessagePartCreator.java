package bhutan.eledger.application.service.epayment.payment.rma;

import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.rma.RmaMessagePart;
import bhutan.eledger.domain.epayment.rma.RmaMsgType;
import lombok.Data;

import java.time.LocalDateTime;

interface RmaMessagePartCreator {

    RmaMessagePart create(RmaRequestDataCreatorContext context);

    @Data(staticConstructor = "of")
    class RmaRequestDataCreatorContext {
        private final RmaMsgType msgType;
        private final PaymentAdvice paymentAdvice;
        private final String currencyCode;
        private final String orderUuid;
        private final LocalDateTime txnTime;
    }
}
