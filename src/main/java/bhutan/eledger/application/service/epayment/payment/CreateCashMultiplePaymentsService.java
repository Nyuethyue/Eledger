package bhutan.eledger.application.service.epayment.payment;

import bhutan.eledger.application.port.in.epayment.payment.CreateCashMultiplePaymentsUseCase;
import bhutan.eledger.application.port.in.epayment.payment.CreateCashPaymentUseCase;
import bhutan.eledger.domain.epayment.payment.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateCashMultiplePaymentsService implements CreateCashMultiplePaymentsUseCase {
    private final CreateCashPaymentUseCase createCashPaymentUseCase;

    @Override
    public Collection<Receipt> create(CreateCashMultiplePaymentsCommand command) {
        return command.getReceipts()
                .stream()
                .map(createCashPaymentUseCase::create)
                .collect(Collectors.toUnmodifiableList());

    }
}
