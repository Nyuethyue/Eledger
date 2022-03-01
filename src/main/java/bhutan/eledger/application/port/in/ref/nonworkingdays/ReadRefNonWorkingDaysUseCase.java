package bhutan.eledger.application.port.in.ref.nonworkingdays;

import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface ReadRefNonWorkingDaysUseCase {
    Collection<RefNonWorkingDays> readAll();
}
