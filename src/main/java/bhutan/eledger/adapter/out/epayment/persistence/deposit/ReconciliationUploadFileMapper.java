package bhutan.eledger.adapter.out.epayment.persistence.deposit;

import bhutan.eledger.domain.epayment.deposit.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class ReconciliationUploadFileMapper {
    ReconciliationUploadFileEntity mapToEntity(ReconciliationUploadFileInfo uploadRecordInfo) {
        ReconciliationUploadFileEntity entity = new ReconciliationUploadFileEntity(
                null,
                uploadRecordInfo.getFilePath(),
                uploadRecordInfo.getBankId(),
                uploadRecordInfo.getStatus(),
                uploadRecordInfo.getUserName(),
                uploadRecordInfo.getCreationDateTime()
        );
        List<ReconciliationUploadRecordEntity> records;
        if(null != uploadRecordInfo.getUploadRows() && !uploadRecordInfo.getUploadRows().isEmpty()) {
            records = uploadRecordInfo.getUploadRows().stream().map(u ->
                    new ReconciliationUploadRecordEntity(
                            null,
                            u.getDepositNumber(), u.getBankTransactionNumber(),
                            u.getBankBranchCode(), u.getBankProcessingDate(),
                            u.getBankAmount(), u.getDepositDateTime(),
                            u.getDepositAmount(), u.getDepositStatus(),
                            u.getCreationDateTime(), u.getRecordStatus()
                    )
            ).collect(Collectors.toUnmodifiableList());
        } else {
            records = null;
        }
        entity.setRecords(records);

        return entity;
    }

    ReconciliationUploadFileInfo mapToDomain(ReconciliationUploadFileEntity fileEntity) {
        List<ReconciliationUploadRecordInfo> records;
        if(null != fileEntity.getRecords()) {
            records = fileEntity.getRecords()
                    .stream()
                    .map(r ->
                            new ReconciliationUploadRecordInfo(
                                    r.getId(),
                                    r.getDepositNumber(),
                                    r.getBankTransactionNumber(),
                                    r.getBankBranchCode(),
                                    r.getBankProcessingDate(),
                                    r.getBankAmount(),
                                    r.getCreationDateTime(),
                                    r.getDepositAmount(),
                                    r.getDepositStatus(),
                                    r.getRecordStatus(),
                                    r.getCreationDateTime()
                            )
                    )
                    .collect(Collectors.toUnmodifiableList());
        } else {
            records = null;
        }

        return ReconciliationUploadFileInfo.withId(
                fileEntity.getId(),
                fileEntity.getFilePath(),
                fileEntity.getBankId(),
                fileEntity.getStatus(),
                fileEntity.getUserName(),
                fileEntity.getCreationDateTime(),
                records
        );
    }
}
