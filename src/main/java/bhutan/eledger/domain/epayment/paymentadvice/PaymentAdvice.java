package bhutan.eledger.domain.epayment.paymentadvice;


import am.iunetworks.lib.common.validation.RecordNotFoundException;
import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Builder(toBuilder = true)
@Getter
@ToString
public class PaymentAdvice {

    private final Long id;
    private final String drn;
    private final LocalDate dueDate;
    private final Period period;
    private final LocalDateTime creationDateTime;
    private final String pan;
    private PaymentAdviceStatus status;
    private final EpTaxpayer taxpayer;
    private final PaymentAdviceBankInfo bankInfo;
    private final Collection<PayableLine> payableLines;

    public BigDecimal getTotalLiabilityAmount() {
        return payableLines
                .stream()
                .map(PayableLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPaidAmountAmount() {
        return payableLines
                .stream()
                .map(PayableLine::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalToBePaidAmountAmount() {
        return payableLines
                .stream()
                .map(PayableLine::getAmountToBePaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<PayableLine> getPayableLineById(Long payableLineId) {
        return payableLines.stream()
                .filter(pl -> pl.getId().equals(payableLineId))
                .findAny();
    }

    public PayableLine getRequiredPayableLineById(Long payableLineId) {
        return getPayableLineById(payableLineId)
                .orElseThrow(() -> new RecordNotFoundException("PayableLine by id: [" + payableLineId + "] not found."));
    }

    public boolean isPaid() {
        return BigDecimal.ZERO.compareTo(getTotalToBePaidAmountAmount()) == 0;
    }

    public PaymentAdvice changeStatus(PaymentAdviceStatus status) {
        this.status = status;

        return this;
    }

    public PaymentAdvice upsertPaymentLine(PayableLine payableLine) {
        payableLines.
                stream()
                .filter(pl -> pl.getGlAccount().getCode().equals(payableLine.getGlAccount().getCode()))
                .findAny()
                .ifPresentOrElse(
                        pl -> pl.updateAmount(payableLine.getAmount()),
                        () -> payableLines.add(payableLine)
                );

        return this;
    }

    @Data(staticConstructor = "of")
    public static class Period {
        private final String year;
        private final String segment;
    }

    public static PaymentAdvice withId(Long id, String drn, LocalDate dueDate, Period period, LocalDateTime creationDateTime, String pan, PaymentAdviceStatus status, EpTaxpayer taxpayer, PaymentAdviceBankInfo bankInfo, Collection<PayableLine> payableLines) {
        return new PaymentAdvice(
                id,
                drn,
                dueDate,
                period,
                creationDateTime,
                pan,
                status,
                taxpayer,
                bankInfo,
                new ArrayList<>(payableLines)
        );
    }

    public static PaymentAdvice withoutId(String drn, LocalDate dueDate, Period period, LocalDateTime creationDateTime, String pan, PaymentAdviceStatus status, EpTaxpayer taxpayer, PaymentAdviceBankInfo bankInfo, Collection<PayableLine> payableLines) {
        return new PaymentAdvice(
                null,
                drn,
                dueDate,
                period,
                creationDateTime,
                pan,
                status,
                taxpayer,
                bankInfo,
                new ArrayList<>(payableLines)
        );
    }
}
