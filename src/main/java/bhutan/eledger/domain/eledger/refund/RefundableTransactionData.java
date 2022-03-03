package bhutan.eledger.domain.eledger.refund;

import lombok.Data;

import java.math.BigDecimal;

@Data(staticConstructor = "of")
public class RefundableTransactionData {
    private final String netNegativeType;
    private final Long transactionId;
    private final String taxTypeCode;
    private final String glAccountCode;
    private final Long glAccountId;
    private final String drn;
    private final String periodYear;
    private final String periodSegment;
    private final BigDecimal balance;
}
