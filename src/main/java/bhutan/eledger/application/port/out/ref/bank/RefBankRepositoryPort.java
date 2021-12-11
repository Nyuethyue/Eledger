package bhutan.eledger.application.port.out.ref.bank;

import bhutan.eledger.domain.ref.bank.RefBank;
import java.util.Optional;
import java.util.Collection;

public interface RefBankRepositoryPort {

    Long create(RefBank refBank);

    Collection<RefBank> readAll();

    void deleteAll();

    Optional<RefBank> readById(Long id);

    Optional<RefBank> readByCode(String code);

    boolean existsByCode(String code);

    boolean existsById(Long id);

    boolean isOpenBankExists(RefBank refBank);

    Collection<RefBank> getBankListByGlPartFullCode(String glPartFullCode);
}
