package bhutan.eledger.adapter.out.persistence.epayment.generatereceipt;

import bhutan.eledger.application.port.out.epayment.generatereceipt.EledgerPaymentTransactionPort;
import bhutan.eledger.domain.epayment.generatereceipt.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
class EledgerPaymentTransactionAdapter implements EledgerPaymentTransactionPort {

    @Override
    public void create(Receipt receipt) {
        log.warn("Not implemented!!!");
    }
}
