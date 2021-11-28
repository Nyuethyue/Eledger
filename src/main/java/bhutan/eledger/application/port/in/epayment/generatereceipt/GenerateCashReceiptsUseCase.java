package bhutan.eledger.application.port.in.epayment.generatereceipt;

import bhutan.eledger.domain.epayment.generatereceipt.Receipt;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface GenerateCashReceiptsUseCase {

    Collection<Receipt> generate(@Valid GenerateCashReceiptsCommand command);

    @Getter
    @ToString
    class GenerateCashReceiptsCommand {
        @Valid
        @NotNull
        @NotEmpty
        private final Collection<GenerateCashReceiptUseCase.GenerateCashReceiptCommand> receipts;

        @JsonCreator
        public GenerateCashReceiptsCommand(Collection<GenerateCashReceiptUseCase.GenerateCashReceiptCommand> receipts) {
            this.receipts = receipts;
        }
    }
}
