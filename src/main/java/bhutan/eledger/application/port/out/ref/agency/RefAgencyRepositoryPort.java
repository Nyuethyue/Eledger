package bhutan.eledger.application.port.out.ref.agency;

import bhutan.eledger.domain.ref.agency.RefAgency;
import bhutan.eledger.domain.ref.bank.RefBank;

import java.util.Collection;

public interface RefAgencyRepositoryPort {

    Long create(RefAgency refAgency);

    Collection<RefAgency> readAll();

    void deleteAll();

    boolean isOpenAgencyExists(RefAgency refAgency);
}
