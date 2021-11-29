package bhutan.eledger.application.service.epayment.generatereceipt;

import bhutan.eledger.application.port.in.epayment.generatereceipt.GenerateCashReceiptUseCase;
import bhutan.eledger.application.port.in.epayment.generatereceipt.GenerateCashReceiptsUseCase;
import bhutan.eledger.application.port.out.epayment.generatereceipt.ReceiptNumberGeneratorPort;
import bhutan.eledger.domain.epayment.generatereceipt.Receipt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class GenerateCashReceiptsService implements GenerateCashReceiptsUseCase {
    private final GenerateCashReceiptUseCase generateCashReceiptUseCase;
    private final ReceiptNumberGeneratorPort receiptNumberGeneratorPort;

    @Override
    public Collection<Receipt> generate(GenerateCashReceiptsCommand command) {
        LocalDateTime creationDateTime = LocalDateTime.now();

        String receiptNumber = receiptNumberGeneratorPort.generate(creationDateTime.toLocalDate());

        log.trace("Receipt number: [{}], generated in: {}", receiptNumber, creationDateTime.toLocalDate());

        return command.getReceipts()
                .stream()
                .map(c -> generateCashReceiptUseCase.generate(receiptNumber, creationDateTime, c))
                .collect(Collectors.toUnmodifiableList());
    }
}
