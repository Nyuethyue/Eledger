package bhutan.eledger.adapter.out.epayment.persistence.taxpayer;


import bhutan.eledger.application.port.out.epayment.taxpayer.EpTaxpayerRepositoryPort;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class EpTaxpayerAdapter implements EpTaxpayerRepositoryPort {

    private final EpTaxpayerEntityRepository taxpayerEntityRepository;

    @Override
    public Optional<EpTaxpayer> readById(String id) {
        return taxpayerEntityRepository.findById(id);
    }

    @Override
    public Optional<EpTaxpayer> readByTpn(String tpn) {
        return taxpayerEntityRepository.findByTpn(tpn);
    }
}
