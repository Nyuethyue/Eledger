package bhutan.eledger.adapter.out.eledger.persistence.taxpayer;


import bhutan.eledger.application.port.out.eledger.taxpayer.ElTaxpayerRepositoryPort;
import bhutan.eledger.domain.eledger.taxpayer.ElTaxpayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class ElTaxpayerAdapter implements ElTaxpayerRepositoryPort {

    private final TaxpayerEntityRepository taxpayerEntityRepository;

    @Override
    public Long create(ElTaxpayer taxpayer) {
        return taxpayerEntityRepository.save(taxpayer).getId();
    }

    @Override
    public Optional<ElTaxpayer> readById(Long id) {
        return taxpayerEntityRepository.findById(id);
    }

    @Override
    public Optional<ElTaxpayer> readByTpn(String tpn) {
        return taxpayerEntityRepository.findByTpn(tpn);
    }

    @Override
    public Collection<ElTaxpayer> readAll() {
        return taxpayerEntityRepository.findAll();
    }

    @Override
    public void deleteAll() {
        taxpayerEntityRepository.deleteAll();
    }
}
