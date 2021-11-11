package bhutan.eledger.domain.eledger.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Data(staticConstructor = "withId")
public class Transaction {
    private final Long id;
    private final String drn;
    private final Long taxpayerId;
    private final String glAccountCode;
    private final LocalDate settlementDate;
    private final BigDecimal amount;
    private final LocalDateTime creationDateTime;
    private final Long transactionTypeId;
    private final Collection<TransactionAttribute> transactionAttributes;

    public static Transaction withoutId(
            String drn,
            Long taxpayerId,
            String glAccountCode,
            LocalDate settlementDate,
            BigDecimal amount,
            LocalDateTime creationDateTime,
            Long transactionTypeId,
            Collection<TransactionAttribute> transactionAttributes
    ) {
        return new Transaction(
                null,
                drn,
                taxpayerId,
                glAccountCode,
                settlementDate,
                amount,
                creationDateTime,
                transactionTypeId,
                transactionAttributes
        );
    }
}
