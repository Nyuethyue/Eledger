package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data()
public class ReconciliationUploadFileInfo {
    private final Long id;
    private final String filePath;
    private final String bankId;
    private final String status;
    private final String userName;
    private final LocalDateTime creationDateTime;
    private final Collection<ReconciliationUploadRecordInfo> uploadRows;

    public static ReconciliationUploadFileInfo withId(
            Long id,
            String filePath,
            String bankId,
            String status,
            String userName,
            LocalDateTime creationDateTime,
            Collection<ReconciliationUploadRecordInfo> uploadRows) {
        return new ReconciliationUploadFileInfo(
                id,
                filePath,
                bankId,
                status,
                userName,
                creationDateTime,
                uploadRows);
    }

}
