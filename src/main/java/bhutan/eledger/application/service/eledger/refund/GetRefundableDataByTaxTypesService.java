package bhutan.eledger.application.service.eledger.refund;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.eledger.refund.GetRefundableDataByTaxTypesUseCase;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartRepositoryPort;
import bhutan.eledger.application.port.out.eledger.config.glaccount.GLAccountPartTypeRepositoryPort;
import bhutan.eledger.application.port.out.eledger.refund.GetRefundableDataByTaxTypesPort;
import bhutan.eledger.domain.eledger.refund.RefundableTransactionData;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetRefundableDataByTaxTypesService implements GetRefundableDataByTaxTypesUseCase {

    private final GetRefundableDataByTaxTypesPort getRefundableDataByTaxTypesPort;
    private final GLAccountPartRepositoryPort glAccountPartRepositoryPort;
    private final GLAccountPartTypeRepositoryPort glAccountPartTypeRepositoryPort;

    @Override
    public Collection<RefundableTransactionData> get(GetRefundDataByTaxTypesommand command) {

        log.debug("Loading refundable transaction data by command: {}", command);

        var partType = glAccountPartTypeRepositoryPort.requiredReadByLevel(5);

        var taxTypes = glAccountPartRepositoryPort.readAllByFullCodesAndPartType(command.getTaxTypeCodes(), partType);

        if (taxTypes.size() != command.getTaxTypeCodes().size()) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation("taxTypeCodes", "Some of Tax types are missing with codes: [" + command.getTaxTypeCodes() + "].")
            );
        }

        var refundableTransactionDatas = getRefundableDataByTaxTypesPort.getByTaxTypes(command.getTpn(), LocalDate.now(), taxTypes);

        log.trace("Loaded refundable data: {}", refundableTransactionDatas);

        return refundableTransactionDatas;
    }
}
