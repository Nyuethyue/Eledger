package bhutan.eledger.application.service.eledger.transaction;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.eledger.transaction.CreateTransactionsUseCase;
import bhutan.eledger.application.port.in.eledger.transaction.CreateTransactionsUseCaseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
class CreateTransactionsServiceFactory implements CreateTransactionsUseCaseFactory {

    private final CreateReturnTransactionsService createReturnTransactionsService;
    private final CreatePaymentTransactionsService createPaymentTransactionsService;
    private final CreateRefundTransactionsService createRefundTransactionsService;

    @Override
    public CreateTransactionsUseCase get(String service) {
        return switch (service) {
            case "return" -> createReturnTransactionsService;
            case "payment" -> createPaymentTransactionsService;
            case "refund" -> createRefundTransactionsService;
            default -> throw new ViolationException(
                    "Incorrect parameter: " + service,
                    new ValidationError()
                            .addViolation("create.service", "Path variable service's value expected to be in: [return, payment, refund]")
            );
        };
    }
}
