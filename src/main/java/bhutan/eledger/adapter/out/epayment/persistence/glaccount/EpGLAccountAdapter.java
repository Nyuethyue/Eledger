package bhutan.eledger.adapter.out.epayment.persistence.glaccount;


import bhutan.eledger.application.port.out.epayment.glaccount.EpGLAccountRepositoryPort;
import bhutan.eledger.domain.epayment.glaccount.EpGLAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class EpGLAccountAdapter implements EpGLAccountRepositoryPort {

    private final EpGLAccountEntityRepository taxpayerEntityRepository;

    @Override
    public EpGLAccount create(EpGLAccount glAccount) {
        return taxpayerEntityRepository.save(glAccount);
    }

    @Override
    public Optional<EpGLAccount> readById(Long id) {
        return taxpayerEntityRepository.findById(id);
    }

    @Override
    public Optional<EpGLAccount> readByCode(String tpn) {
        return taxpayerEntityRepository.findByCode(tpn);
    }

    @Override
    public void deleteAll() {
        taxpayerEntityRepository.deleteAll();
    }
}
