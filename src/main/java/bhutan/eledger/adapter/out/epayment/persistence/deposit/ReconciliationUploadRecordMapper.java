package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.domain.epayment.deposit.*;
import org.springframework.stereotype.Component;

@Component
class ReconciliationUploadRecordMapper {

    ReconciliationUploadRecordEntity mapToEntity(ReconciliationUploadRecordInfo info) {
        ReconciliationUploadRecordEntity entity = new ReconciliationUploadRecordEntity(
                null,
                info.getDepositNumber(),
                info.getBankTransactionNumber(),
                info.getBankBranchCode(),
                info.getBankProcessingDate(),
                info.getBankAmount(),
                info.getDepositDate(),
                info.getDepositAmount(),
                info.getDepositStatus(),
                info.getCreationDateTime(),
                info.getRecordStatus()
        );

        return entity;
    }

    ReconciliationUploadRecordInfo mapToDomain(ReconciliationUploadRecordEntity entity) {
        return new ReconciliationUploadRecordInfo(
                entity.getId(),
                entity.getDepositNumber(),
                entity.getBankTransactionNumber(),
                entity.getBankBranchCode(),
                entity.getBankProcessingDate(),
                entity.getBankAmount(),

                entity.getDepositDate(),
                entity.getDepositAmount(),
                entity.getDepositStatus(),
                entity.getRecordStatus(),
                entity.getCreationDateTime()
        );
    }
}
