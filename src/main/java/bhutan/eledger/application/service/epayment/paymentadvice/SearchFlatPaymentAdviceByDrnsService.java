package bhutan.eledger.application.service.epayment.paymentadvice;

import bhutan.eledger.application.port.in.epayment.paymentadvice.SearchFlatPaymentAdviceByDrnsUseCase;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.paymentadvice.FlatPaymentAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SearchFlatPaymentAdviceByDrnsService implements SearchFlatPaymentAdviceByDrnsUseCase {

    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;

    @Override
    public Collection<FlatPaymentAdvice> searchByDrns(SearchPaymentAdviceByDrnCommand command) {
        log.trace("Search execution started with in command: {}", command);

        return paymentAdviceRepositoryPort
                .readAllFlatByDrns(command
                        .getDrnCommands()
                        .stream()
                        .map(drnCommand -> drnCommand.getDrn())
                        .collect(Collectors.toList()));
    }
}
