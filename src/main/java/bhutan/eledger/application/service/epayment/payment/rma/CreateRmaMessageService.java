package bhutan.eledger.application.service.epayment.payment.rma;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.epayment.payment.rma.CreateRmaMessageUseCase;
import bhutan.eledger.application.port.out.epayment.paymentadvice.PaymentAdviceRepositoryPort;
import bhutan.eledger.application.port.out.epayment.rma.RmaMessageRepositoryPort;
import bhutan.eledger.common.ref.refentry.RefEntryRepository;
import bhutan.eledger.common.ref.refentry.RefName;
import bhutan.eledger.domain.epayment.rma.RmaMessage;
import bhutan.eledger.domain.epayment.rma.RmaMsgType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class CreateRmaMessageService implements CreateRmaMessageUseCase {
    private final RefEntryRepository refEntryRepository;
    private final PaymentAdviceRepositoryPort paymentAdviceRepositoryPort;
    private final RmaMessagePartCreatorService rmaRequestDataCreatorService;
    private final RmaMessageRepositoryPort rmaMessageRepositoryPort;

    @Override
    public RmaMessage create(CreateRmaMessageCommand command) {
        log.trace("Creating rma payment with command: {}", command);

        var paymentAdvice = paymentAdviceRepositoryPort.requiredReadById(command.getPaymentAdviceId());

        if (!paymentAdvice.canBeInitiated()) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "status",
                                    "Payment advice can't be initiated from status: " + paymentAdvice.getStatus()
                            )
            );
        }

        var refCurrencyEntry = refEntryRepository.findByRefNameAndCode(
                RefName.CURRENCY.getValue(),
                "BTN"
        );

        String uuid = UUID.randomUUID().toString().replace("-", "");

        var creationDateTime = LocalDateTime.now();

        var rmaMessagePart = rmaRequestDataCreatorService.create(
                RmaMessagePartCreator.RmaRequestDataCreatorContext.of(
                        RmaMsgType.AR,
                        paymentAdvice,
                        refCurrencyEntry.getCode(),
                        uuid,
                        creationDateTime
                )
        );

        RmaMessage rmaMessage = RmaMessage.createWithAr(
                paymentAdvice.getId(),
                uuid,
                creationDateTime,
                rmaMessagePart
        );

        log.trace("Persisting rma message: {}", rmaMessagePart);

        RmaMessage persistedRmaMessage = rmaMessageRepositoryPort.create(rmaMessage);

        log.debug("Rma message with id: {} successfully created.", persistedRmaMessage.getId());

        return persistedRmaMessage;

    }
}
