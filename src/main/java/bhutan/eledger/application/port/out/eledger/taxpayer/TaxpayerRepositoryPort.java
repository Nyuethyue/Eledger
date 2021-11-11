package bhutan.eledger.application.port.out.eledger.taxpayer;

import bhutan.eledger.domain.eledger.taxpayer.Taxpayer;

import java.util.Optional;

public interface TaxpayerRepositoryPort {

    Long create(Taxpayer taxpayer);

    Optional<Taxpayer> readById(Long id);

    Optional<Taxpayer> readByTpn(String tpn);

    void deleteAll();
}
