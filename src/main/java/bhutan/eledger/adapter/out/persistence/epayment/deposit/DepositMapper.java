package bhutan.eledger.adapter.out.persistence.epayment.deposit;

import bhutan.eledger.domain.epayment.deposit.Deposit;
import bhutan.eledger.domain.epayment.deposit.DepositStatus;
import bhutan.eledger.domain.epayment.payment.PaymentMode;
import org.springframework.stereotype.Component;


@Component
public class DepositMapper {

    DepositEntity mapToEntity(Deposit deposit) {
        DepositEntity depositEntity = new DepositEntity(
                deposit.getId(),
                deposit.getPaymentMode().getValue(),
                deposit.getBankDepositDate(),
                deposit.getLastPrintedDate(),
                deposit.getAmount(),
                deposit.getStatus().getValue(),
                deposit.getCreationDateTime(),
                deposit.getTaxpayer()
        );

        return depositEntity;
    }

    Deposit mapToDomain(DepositEntity deposit) {
        return new Deposit(
                deposit.getId(),
                PaymentMode.of(deposit.getPaymentMode()),
                deposit.getBankDepositDate(),
                deposit.getLastPrintedDate(),
                deposit.getAmount(),
                DepositStatus.of(deposit.getStatus()),
                deposit.getTaxpayer(),
                deposit.getCreationDateTime()
        );
    }
}
