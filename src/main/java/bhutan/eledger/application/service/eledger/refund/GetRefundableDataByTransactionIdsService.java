package bhutan.eledger.application.service.eledger.refund;

import bhutan.eledger.application.port.in.eledger.refund.GetRefundDataByTransactionIdsUseCase;
import bhutan.eledger.application.port.out.eledger.refund.GetRefundableDataByTransactionIdsPort;
import bhutan.eledger.domain.eledger.refund.RefundableTransactionData;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetRefundableDataByTransactionIdsService implements GetRefundDataByTransactionIdsUseCase {

    private final GetRefundableDataByTransactionIdsPort getRefundableDataByTransactionIdsPort;

    @Override
    public Collection<RefundableTransactionData> get(GetRefundDataByTransactionIdsCommand command) {

        log.debug("Loading refundable transaction data by command: {}", command);

        var refundableTransactionDatas = getRefundableDataByTransactionIdsPort.getByTransactionIds(command.getTpn(), LocalDate.now(), command.getTransactionIds());

        log.trace("Loaded refundable data: {}", refundableTransactionDatas);

        return refundableTransactionDatas;
    }
}
