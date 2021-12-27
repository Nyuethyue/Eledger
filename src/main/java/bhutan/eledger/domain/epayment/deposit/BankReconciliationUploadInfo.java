package bhutan.eledger.domain.epayment.deposit;

import lombok.Data;

import java.util.Collection;

@Data()
public class BankReconciliationUploadInfo {
    private String uploadId;
    private String uploadDate;
    private String uploadFilePath;
    private Long userId;
    private Collection<BankStatementImportReconciliationInfo> uploadRows;
}
