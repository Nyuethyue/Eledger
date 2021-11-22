package bhutan.eledger.application.port.out.epayment.glaccount;

import bhutan.eledger.domain.epayment.glaccount.EpGLAccount;

import java.util.Optional;

public interface EpGLAccountRepositoryPort {

    EpGLAccount create(EpGLAccount taxpayer);

    Optional<EpGLAccount> readById(Long id);

    Optional<EpGLAccount> readByCode(String tpn);

    void deleteAll();
}
