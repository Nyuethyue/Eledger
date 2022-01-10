package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.domain.epayment.deposit.*;
import org.springframework.stereotype.Component;

@Component
class ReconciliationUploadRecordMapper {

    ReconciliationUploadRecordInfo mapToDomain(ReconciliationUploadRecordEntity entity) {
        return new ReconciliationUploadRecordInfo(
                entity.getId(),
                entity.getDepositNumber(),
                entity.getBankTransactionNumber(),
                entity.getBankBranchCode(),
                entity.getBankProcessingDate(),
                entity.getBankAmount(),

                entity.getDepositDateTime(),
                entity.getDepositAmount(),
                entity.getDepositStatus(),
                entity.getRecordStatus(),
                entity.getCreationDateTime()
        );
    }
}
