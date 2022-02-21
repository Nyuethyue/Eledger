package bhutan.eledger.application.port.out.epayment.rma;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.epayment.rma.RmaMessage;

import java.util.Optional;

public interface RmaMessageRepositoryPort {

    RmaMessage create(RmaMessage rmaMessage);

    void update(RmaMessage rmaMessage);

    Optional<RmaMessage> readById(Long id);

    default RmaMessage requiredReadById(Long id) {
        return readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("RmaMessage by id: [" + id + "] not found.")
                );
    }

    Optional<RmaMessage> readByOrderNo(String orderNo);

    default RmaMessage requiredReadByOrderNo(String orderNo) {
        return readByOrderNo(orderNo)
                .orElseThrow(() ->
                        new RecordNotFoundException("RmaMessage by orderNo: [" + orderNo + "] not found.")
                );
    }
}
