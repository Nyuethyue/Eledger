package bhutan.eledger.application.service.epayment.paymentadvice;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.epayment.paymentadvice.CreatePaymentAdviceUseCase;
import bhutan.eledger.application.port.out.epayment.config.bank.GetBankInfoByGLCodeAccountPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceNumberGeneratorPort;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdvice;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceBankInfo;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentAdviceStatus;
import bhutan.eledger.domain.epayment.paymentadvice.PaymentLine;
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

    @Override
    public Long create(CreatePaymentAdviceCommand command) {
        log.trace("Generating payment advice by command: {}", command);

        var anyGlCode = command.getPaymentLines()
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
                command.getTpn(),
                command.getDueDate(),
                PaymentAdvice.Period.of(command.getPeriod().getStart(), command.getPeriod().getEnd()),
                creationDateTime,
                pan,
                PaymentAdviceStatus.INITIAL,
                bankInfo,
                command.getPaymentLines()
                        .stream()
                        .map(plc ->
                                PaymentLine.withoutId(
                                        PaymentLine.GLAccount.withoutId(
                                                plc.getGlAccount().getCode(),
                                                Multilingual.fromMap(plc.getGlAccount().getDescription())
                                        ),
                                        BigDecimal.ZERO,
                                        plc.getAmount()
                                )
                        )
                        .collect(Collectors.toUnmodifiableSet())
        );
    }
}
