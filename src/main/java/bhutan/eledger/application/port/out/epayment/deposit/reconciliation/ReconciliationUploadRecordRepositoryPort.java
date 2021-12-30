package bhutan.eledger.application.port.out.epayment.deposit.reconciliation;

import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.epayment.deposit.ReconciliationUploadRecordInfo;

import java.util.Collection;
import java.util.Optional;

public interface ReconciliationUploadRecordRepositoryPort {

    Optional<ReconciliationUploadRecordInfo> readById(Long id);

    default ReconciliationUploadRecordInfo requiredReadById(Long id) {
        return readById(id)
                .orElseThrow(() ->
                        new RecordNotFoundException("ReconciliationUploadRecord by id: [" + id + "] not found.")
                );
    }

    Collection<ReconciliationUploadRecordInfo> readAll();

    ReconciliationUploadRecordInfo create(ReconciliationUploadRecordInfo paymentAdvice);

    void deleteAll();
}
