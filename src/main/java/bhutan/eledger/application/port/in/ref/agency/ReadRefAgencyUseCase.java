package bhutan.eledger.application.port.in.ref.agency;

import bhutan.eledger.domain.ref.agency.RefAgency;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;

@Validated
public interface ReadRefAgencyUseCase {

    Collection<RefAgency> readAll();
}
