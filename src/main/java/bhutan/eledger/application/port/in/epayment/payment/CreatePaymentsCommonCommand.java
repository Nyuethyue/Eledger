package bhutan.eledger.application.port.in.epayment.payment;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collection;

@Getter
@ToString
@RequiredArgsConstructor
public class CreatePaymentsCommonCommand<T extends CreatePaymentCommonCommand> {
    @NotNull
    private final Long refCurrencyId;

    @Valid
    @NotNull
    @NotEmpty
    private final Collection<T> receipts;
}
