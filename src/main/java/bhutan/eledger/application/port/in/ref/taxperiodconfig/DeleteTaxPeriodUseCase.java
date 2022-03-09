package bhutan.eledger.application.port.in.ref.taxperiodconfig;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
public interface DeleteTaxPeriodUseCase {

    void delete(@Valid DeleteTaxPeriodCommand command);

    @Data
    class DeleteTaxPeriodCommand {
        private final Long id;
    }
}
