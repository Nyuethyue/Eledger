package bhutan.eledger.application.port.in.epayment.deposit;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface ApproveReconciliationUseCase {

    void approveDepositReconciliation(@Valid ApproveReconciliationUseCase.ApproveDepositReconciliationCommand command);

    @Data
    class ApproveDepositReconciliationCommand {
        private final Long uploadId;

        @NotNull
        @NotEmpty
        private final List<String> depositNumbers;

        @JsonCreator
        public ApproveDepositReconciliationCommand(@JsonProperty("uploadId") Long uploadId,
                                                   @JsonProperty("depositNumbers") List<String> depositNumbers) {
            this.uploadId = uploadId;
            this.depositNumbers = depositNumbers;
        }
    }
}