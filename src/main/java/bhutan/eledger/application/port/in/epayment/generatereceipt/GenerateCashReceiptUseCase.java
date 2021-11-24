package bhutan.eledger.application.port.in.epayment.generatereceipt;

import bhutan.eledger.domain.epayment.generatereceipt.Receipt;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;

@Validated
public interface GenerateCashReceiptUseCase {

    Receipt generate(@Valid GenerateCashReceiptCommand command);

    class GenerateCashReceiptCommand extends GenerateReceiptCommonCommand {

        public GenerateCashReceiptCommand(Long paymentAdviceId, String currency, Collection<PaymentCommand> payments) {
            super(paymentAdviceId, currency, payments);
        }
    }
}
