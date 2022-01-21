package bhutan.eledger.domain.eledger.reporting.taxpayeraccount;

import lombok.Data;

import java.math.BigDecimal;

@Data(staticConstructor = "of")
public class TaxpayerAccountSspDto {
    private final String rowType;
    private final String transactionDate;
    private final String description;
    private final BigDecimal debit;
    private final BigDecimal credit;
    private final BigDecimal balance;
    private final String drn;
}
