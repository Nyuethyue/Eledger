package bhutan.eledger.application.service.epayment.generatereceipt;

import bhutan.eledger.application.port.in.epayment.generatereceipt.GenerateCashReceiptUseCase;
import bhutan.eledger.application.port.in.epayment.generatereceipt.GenerateCashReceiptsUseCase;
import bhutan.eledger.domain.epayment.generatereceipt.Receipt;
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
class GenerateCashReceiptsService implements GenerateCashReceiptsUseCase {
    private final GenerateCashReceiptUseCase generateCashReceiptUseCase;

    @Override
    public Collection<Receipt> generate(GenerateCashReceiptsCommand command) {
        return command.getReceipts()
                .stream()
                .map(generateCashReceiptUseCase::generate)
                .collect(Collectors.toUnmodifiableList());
    }
}
