package bhutan.eledger.application.service.epayment.paymentadvice;

import am.iunetworks.lib.multilingual.core.Multilingual;
import bhutan.eledger.application.port.in.epayment.paymentadvice.UpsertPaymentAdviceUseCase;
import bhutan.eledger.application.port.out.epayment.glaccount.EpGLAccountRepositoryPort;
import bhutan.eledger.domain.epayment.glaccount.EpGLAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
//todo remove and add for each persistence row new GL account
class GLAccountResolverService {
    private final EpGLAccountRepositoryPort epGLAccountRepositoryPort;

    EpGLAccount resolve(UpsertPaymentAdviceUseCase.GLAccountCommand glAccountCommand) {
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
