package bhutan.eledger.domain.eledger.reporting.glaccountdetails;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data(staticConstructor = "of")
public class GlAccountDetailsDto {

    private final LocalDate transactionDate;
    private final String glAccountId;
    private final String glAccountCode;
    private final String glAccountDescription;
    private final String periodYear;
    private final String periodSegment;
    private final BigDecimal amount;
    private final BigDecimal netNegative;
    private final BigDecimal totalLiability;
    private final BigDecimal totalInterest;
    private final BigDecimal totalPenalty;
    private final BigDecimal payment;
    private final BigDecimal nonRevenue;
    private final String drn;
    private final String tpn;
}
