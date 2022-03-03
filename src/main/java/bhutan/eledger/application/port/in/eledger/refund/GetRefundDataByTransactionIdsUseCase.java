package bhutan.eledger.application.port.in.eledger.refund;

import bhutan.eledger.domain.eledger.refund.RefundData;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface GetRefundDataByTransactionIdsUseCase {

    Collection<RefundData> get(GetRefundDataByTransactionIdsCommand command);

    @Data
    class GetRefundDataByTransactionIdsCommand {
        private final String tpn;
        private final Collection<Long> transactionIds;
    }
}
