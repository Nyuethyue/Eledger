package bhutan.eledger.application.port.out.epayment.rma;

import bhutan.eledger.domain.epayment.rma.RmaMessageResponse;

import java.util.Optional;

public interface RmaMessageResponseRepositoryPort {
    Optional<RmaMessageResponse> findLastResponseByOrderNo(String orderNo);
}
