package bhutan.eledger.application.service.deposit;

import bhutan.eledger.application.port.in.epayment.deposit.CreateDepositUseCase;
import bhutan.eledger.application.port.out.epayment.deposit.DepositRepositoryPort;
import bhutan.eledger.application.port.out.epayment.payment.CashReceiptRepositoryPort;
import bhutan.eledger.application.port.out.epayment.payment.EledgerPaymentTransactionPort;
import bhutan.eledger.application.port.out.epayment.taxpayer.EpTaxpayerRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntryRepository;
import bhutan.eledger.domain.epayment.deposit.Deposit;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateDepositService implements CreateDepositUseCase {
    private final DepositRepositoryPort depositRepositoryPort;
    private final CashReceiptRepositoryPort cashReceiptRepositoryPort;
    private final EpTaxpayerRepositoryPort epTaxpayerRepositoryPort;
    private final EledgerPaymentTransactionPort eledgerPaymentTransactionPort;
    private final RefEntryRepository refEntryRepository;

    @Override
    public Long create(CreateDepositUseCase.CreateDepositCommand command) {
        log.trace("Generating Deposit by command: {}", command);

        LocalDateTime creationDateTime = LocalDateTime.now();

        var deposit = Deposit.withoutId(
                command.getPaymentMode(),
                command.getBankDepositDate(),
                command.getAmount(),
                command.getStatus(),
                command.getTaxpayer()
        );


        log.trace("Creating eledger payment deposit: {}", deposit);

        var id = depositRepositoryPort.create(deposit);

        return id;
    }
}
