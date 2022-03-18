package bhutan.eledger.application.port.out.epayment.glaccount;

import am.iunetworks.lib.common.persistence.spring.repository.ReadOnlyRepository;
import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.epayment.glaccount.EpGLAccount;

import java.util.Optional;

public interface EpGLAccountRepositoryPort extends ReadOnlyRepository<EpGLAccount, Long> {

    Optional<EpGLAccount> readById(Long id);

    Optional<EpGLAccount> readByCode(String tpn);

    default EpGLAccount requiredReadByCode(String code) {
        return readByCode(code).orElseThrow(() -> new RecordNotFoundException("GL account by code: " + code + " not found."));
    }
}
