package bhutan.eledger.application.port.out.ref.nonworkingdays;

import bhutan.eledger.domain.ref.nonworkingdays.RefNonWorkingDays;

import java.util.Collection;
import java.util.Optional;

public interface RefNonWorkingDaysRepositoryPort {
    Collection<RefNonWorkingDays> create(Collection<RefNonWorkingDays> nonWorkingDays);

    Collection<RefNonWorkingDays> readAll();

    void deleteAll();

    Optional<RefNonWorkingDays> readById(Long id);

}
