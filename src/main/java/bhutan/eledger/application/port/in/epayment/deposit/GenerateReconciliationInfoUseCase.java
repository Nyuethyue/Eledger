package bhutan.eledger.application.port.in.epayment.deposit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Validated
public interface GenerateReconciliationInfoUseCase {

    ReconciliationInfo generate(@Valid GenerateDepositReconciliationInfoCommand command);

    @Data
    class GenerateDepositReconciliationInfoCommand {
        private final String filePath;

        @JsonCreator
        public GenerateDepositReconciliationInfoCommand(@JsonProperty("filePath") String filePath) {
            this.filePath = filePath;
        }
    }

    @Data
    class ReconciliationInfo {
        private final boolean ok;
        private final Collection<DepositReconciliationInfo> deposits;
        private final Collection<ErrorRecordsInfo> errorRecords;
    }

    @Data
    class DepositReconciliationInfo {
        private final String bankTransactionNumber;
        private final String bankBranchCode;
        private final LocalDate bankProcessingDate;
        private final BigDecimal bankAmount;

        private final String depositNumber;
        private final LocalDateTime depositDate;
        private final BigDecimal depositAmount;
    }

    @Data
    class ErrorRecordsInfo {
        private final String errorType;
        private final String transactionId;
        private final String bankBranchCode;
        private final String depositNumber;
        private final LocalDate paymentDate;
        private final BigDecimal amount;
    }
}