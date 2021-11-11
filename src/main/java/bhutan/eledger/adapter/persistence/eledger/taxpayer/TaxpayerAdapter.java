package bhutan.eledger.adapter.persistence.eledger.taxpayer;


import bhutan.eledger.application.port.out.eledger.taxpayer.TaxpayerRepositoryPort;
import bhutan.eledger.domain.eledger.taxpayer.Taxpayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class TaxpayerAdapter implements TaxpayerRepositoryPort {

    private final TaxpayerEntityRepository taxpayerEntityRepository;

    @Override
    public Long create(Taxpayer taxpayer) {
        return taxpayerEntityRepository.save(taxpayer).getId();
    }

    @Override
    public Optional<Taxpayer> readById(Long id) {
        return taxpayerEntityRepository.findById(id);
    }

    @Override
    public Optional<Taxpayer> readByTpn(String tpn) {
        return taxpayerEntityRepository.findByTpn(tpn);
    }

    @Override
    public void deleteAll() {
        taxpayerEntityRepository.deleteAll();
    }
}
