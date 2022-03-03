package bhutan.eledger.application.port.in.eledger.refund;

import bhutan.eledger.domain.eledger.refund.RefundableTransactionData;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Validated
public interface GetRefundableDataByTaxTypesUseCase {

    Collection<RefundableTransactionData> get(@Valid GetRefundDataByTaxTypesommand command);

    @Data
    class GetRefundDataByTaxTypesommand {
        @NotNull
        private final String tpn;
        @NotNull
        @NotEmpty
        private final Collection<String> taxTypeCodes;
    }
}
