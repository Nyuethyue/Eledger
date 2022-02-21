package bhutan.eledger.application.port.out.epayment.rma;

import bhutan.eledger.domain.epayment.rma.RmaMessagePart;
import bhutan.eledger.domain.epayment.rma.RmaMessageResponse;

public interface RmaRequesterPort {

    RmaMessageResponse send(RmaMessagePart rmaMessagePart);
}
