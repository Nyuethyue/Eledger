package bhutan.eledger.application.service.epayment.paymentadvice;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.out.epayment.config.bank.GetBankInfoByGLCodeAccountPort;
import bhutan.eledger.application.port.out.epayment.glaccount.EpGLAccountRepositoryPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.application.port.out.epayment.taxpayer.EpTaxpayerRepositoryPort;
import bhutan.eledger.domain.epayment.glaccount.EpGLAccount;
import bhutan.eledger.domain.epayment.paymentadvice.PayableLine;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceBankInfo;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreatePaymentAdviceService implements CreatePaymentAdviceUseCase {
    private final GetBankInfoByGLCodeAccountPort getBankInfoByGLCodeAccountPort;
    private final PaymentAdviceNumberGeneratorPort paymentAdviceNumberGeneratorPort;
    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;
    private final EpTaxpayerRepositoryPort taxpayerRepositoryPort;
    private final EpGLAccountRepositoryPort epGLAccountRepositoryPort;

    @Override
    public Long create(CreatePaymentAdviceCommand command) {
        log.trace("Generating payment advice by command: {}", command);

        var anyGlCode = command.getPayableLines()
                .stream()
                .findAny()
                .orElseThrow()
                .getGlAccount()
                .getCode();

        var paBankInfo = getBankInfoByGLCodeAccountPort.getBankInfo(
                anyGlCode
        );

        log.trace("Bank information resolved by gl code: {} is: {}", anyGlCode, paBankInfo);

        LocalDateTime creationDateTime = LocalDateTime.now();
        LocalDate creationDate = creationDateTime.toLocalDate();

        var pan = paymentAdviceNumberGeneratorPort.generate(creationDate);

        log.trace("Payment advice number: [{}], generated in: {}", pan, creationDate);

        var paymentAdvice = mapToPaymentAdvice(command, creationDateTime, pan, paBankInfo);

        log.trace("Persisting payment: {}", paymentAdvice);

        var id = paymentAdviceRepositoryPort.create(paymentAdvice);

        log.debug("Payment advice with id: {} successfully created.", id);

        return id;
    }

    private PaymentAdvice mapToPaymentAdvice(CreatePaymentAdviceCommand command, LocalDateTime creationDateTime, String pan, PaymentAdviceBankInfo bankInfo) {

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
                                        resolveGlAccount(plc.getGlAccount()),
                                        BigDecimal.ZERO,
                                        plc.getAmount()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    private EpTaxpayer resolveTaxpayer(TaxpayerCommand taxpayerCommand) {
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

    private EpGLAccount resolveGlAccount(GLAccountCommand glAccountCommand) {
        EpGLAccount result;

        var glAccountOptional = epGLAccountRepositoryPort.readByCode(glAccountCommand.getCode());

        if (glAccountOptional.isPresent()) {
            result = glAccountOptional.get();
        } else {
            var taxpayer = EpGLAccount.withoutId(
                    glAccountCommand.getCode(),
                    LocalDateTime.now(),
                    Multilingual.fromMap(glAccountCommand.getDescriptions())
            );

            log.trace("Persisting taxpayer info: {}", taxpayer);

            result = epGLAccountRepositoryPort.create(
                    taxpayer
            );

            log.debug("Taxpayer info with id: {} successfully created.", result);
        }

        return result;
    }
}
