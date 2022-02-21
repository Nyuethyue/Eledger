package bhutan.eledger.application.service.epayment.paymentadvice;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdviceUseCase;
import bhutan.eledger.application.port.out.epayment.config.bank.GetPrimaryBankAccountRefEntryByGLCodeAccountPort;
import bhutan.eledger.application.port.out.epayment.glaccount.EpGLAccountRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.application.port.out.epayment.taxpayer.EpTaxpayerRepositoryPort;
import bhutan.eledger.domain.epayment.paymentadvice.PayableLine;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceBankInfo;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreatePaymentAdviceService implements CreatePaymentAdviceUseCase {
    private final PaymentAdviceNumberGeneratorPort paymentAdviceNumberGeneratorPort;
    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;
    private final EpTaxpayerRepositoryPort taxpayerRepositoryPort;
    private final EpGLAccountRepositoryPort epGLAccountRepositoryPort;
    private final GLAccountResolverService glAccountResolverService;
    private final GetPrimaryBankAccountRefEntryByGLCodeAccountPort getPrimaryBankAccountRefEntryByGLCodeAccountPort;

    @Override
    public Long create(UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand command) {
        log.trace("Generating payment advice by command: {}", command);

        var anyGlCode = command.getPayableLines()
                .stream()
                .findAny()
                .orElseThrow()
                .getGlAccount()
                .getCode();

        LocalDateTime creationDateTime = LocalDateTime.now();
        LocalDate creationDate = creationDateTime.toLocalDate();

        var bankAccRefEntry = getPrimaryBankAccountRefEntryByGLCodeAccountPort.getPrimaryBankAccountByGLCode(anyGlCode, creationDate);

        var paBankInfo = PaymentAdviceBankInfo.withoutId(
                bankAccRefEntry.getCode(),
                Multilingual.copyOf(bankAccRefEntry.getDescription())
        );

        log.trace("Bank information resolved by gl code: {} is: {}", anyGlCode, paBankInfo);

        var pan = paymentAdviceNumberGeneratorPort.generate(creationDate);

        log.trace("Payment advice number: [{}], generated in: {}", pan, creationDate);

        var paymentAdvice = mapToPaymentAdvice(command, creationDateTime, pan, paBankInfo);

        log.trace("Persisting payment: {}", paymentAdvice);

        var id = paymentAdviceRepositoryPort.create(paymentAdvice);

        log.debug("Payment advice with id: {} successfully created.", id);

        return id;
    }

    private PaymentAdvice mapToPaymentAdvice(UpsertPaymentAdviceUseCase.UpsertPaymentAdviceCommand command, LocalDateTime creationDateTime, String pan, PaymentAdviceBankInfo bankInfo) {

        return PaymentAdvice.withoutId(
                command.getDrn(),
                command.getDueDate(),
                PaymentAdvice.Period.of(
                        command.getPeriod().getYear(),
                        command.getPeriod().getSegment()
                ),
                creationDateTime,
                pan,
                PaymentAdviceStatus.INITIAL,
                resolveTaxpayer(command.getTaxpayer()),
                bankInfo,
                command.getPayableLines()
                        .stream()
                        .map(plc ->
                                PayableLine.withoutId(
                                        glAccountResolverService.resolve(plc.getGlAccount()),
                                        plc.getAmount(),
                                        plc.getTransactionId()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    private EpTaxpayer resolveTaxpayer(UpsertPaymentAdviceUseCase.TaxpayerCommand taxpayerCommand) {
        EpTaxpayer result;

        var taxpayerOptional = taxpayerRepositoryPort.readByTpn(taxpayerCommand.getTpn());

        if (taxpayerOptional.isPresent()) {
            result = taxpayerOptional.get();
        } else {
            var taxpayer = EpTaxpayer.withoutId(
                    taxpayerCommand.getTpn(),
                    taxpayerCommand.getName(),
                    LocalDateTime.now()
            );

            log.trace("Persisting taxpayer info: {}", taxpayer);

            result = taxpayerRepositoryPort.create(
                    taxpayer
            );

            log.debug("Taxpayer info with id: {} successfully created.", result);
        }

        return result;
    }

}
