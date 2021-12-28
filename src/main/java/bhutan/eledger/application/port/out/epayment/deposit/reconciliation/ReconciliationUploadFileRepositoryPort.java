package bhutan.eledger.application.port.out.epayment.deposit.reconciliation;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadFileInfo;

import java.util.Collection;
import java.util.Optional;

public interface ReconciliationUploadFileRepositoryPort {
    Optional<ReconciliationUploadFileInfo> readById(Long id);

    default ReconciliationUploadFileInfo requiredReadById(Long id) {
        return readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("ReconciliationUploadFile by id: [" + id + "] not found.")
                );
    }

    Collection<ReconciliationUploadFileInfo> readAll();

    ReconciliationUploadFileInfo create(ReconciliationUploadFileInfo data);

    void deleteAll();
}
