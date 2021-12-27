package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

@Data()
public class ReconciliationUploadFileInfo {
    private String id;
    private String filePath;
    private String bankId;
    private String status;
    private String userName;
    private LocalDateTime creationDateTime;
    private Collection<ReconciliationUploadRecordInfo> uploadRows;
}
