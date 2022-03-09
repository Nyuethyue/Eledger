package bhutan.eledger.application.service.ref.taxperiodconfig;

import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
import bhutan.eledger.application.port.in.ref.taxperiodconfig.DeleteTaxPeriodUseCase;
import bhutan.eledger.application.port.out.ref.taxperiodconfig.RefTaxPeriodRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
class DeleteTaxPeriodService implements DeleteTaxPeriodUseCase {

    private final RefTaxPeriodRepositoryPort refTaxPeriodRepositoryPort;

    @Override
    public void delete(DeleteTaxPeriodCommand command) {
        var taxPeriod =
                refTaxPeriodRepositoryPort.findById(command.getId());
        if(taxPeriod.isPresent()) {
            if(!taxPeriod.get().getValidFrom().isAfter(LocalDate.now())) {
                throw new ViolationException(
                        new ValidationError()
                                .addViolation("validFrom", "Tax period can not be deleted for past date:")
                );

            }
        }

        refTaxPeriodRepositoryPort.delete(command.getId());
    }
}
