package bhutan.eledger.application.port.out.ref.bankbranch;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.ref.bankbranch.RefBankBranch;


import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public interface RefBankBranchRepositoryPort {

    Long create(RefBankBranch refBankBranch);

    Collection<RefBankBranch> readAll();

    void deleteAll();

    Optional<RefBankBranch> readById(Long id);

    boolean existsByCode(String code);

    Collection<RefBankBranch> readAllByBankId(Long bankId, LocalDate currentDate);

    boolean existsById(Long id);

    boolean isOpenBranchExists(RefBankBranch refBankBranch);

    default RefBankBranch requiredReadById(Long id) {
        return readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("RefBankBranch by id: [" + id + "] not found.")
                );
    }
}
