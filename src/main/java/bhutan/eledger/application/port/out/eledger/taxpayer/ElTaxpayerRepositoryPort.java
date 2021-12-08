package bhutan.eledger.application.port.out.eledger.taxpayer;

import bhutan.eledger.domain.eledger.taxpayer.ElTaxpayer;

import java.util.Collection;
import java.util.Optional;

public interface ElTaxpayerRepositoryPort {

    Long create(ElTaxpayer taxpayer);

    Optional<ElTaxpayer> readById(Long id);

    Optional<ElTaxpayer> readByTpn(String tpn);

    Collection<ElTaxpayer> readAll();

    void deleteAll();
}
