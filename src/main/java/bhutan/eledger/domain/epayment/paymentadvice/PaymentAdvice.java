package bhutan.eledger.domain.epayment.paymentadvice;


import am.iunetworks.lib.common.validation.RecordNotFoundException;
import am.iunetworks.lib.common.validation.ValidationError;
import am.iunetworks.lib.common.validation.ViolationException;
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
    //todo replace bank info by ref entry
    private final PaymentAdviceBankInfo bankInfo;
    private final Collection<PayableLine> payableLines;
    private BigDecimal totalLiabilityAmount;
    private BigDecimal totalPaidAmount;
    private BigDecimal totalToBePaidAmount;

    public BigDecimal getTotalLiabilityAmount() {
        if (totalLiabilityAmount == null) {
            recalculateTotalLiabilityAmount();
        }

        return totalLiabilityAmount;
    }

    public BigDecimal getTotalPaidAmount() {
        if (totalPaidAmount == null) {
            recalculateTotalPaidAmount();
        }

        return totalPaidAmount;
    }

    public BigDecimal getTotalToBePaidAmount() {
        if (totalToBePaidAmount == null) {
            recalculateTotalToBePaidAmount();
        }

        return totalToBePaidAmount;
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

    public void initiate() {
        if (status == PaymentAdviceStatus.SPLIT_PAYMENT || status == PaymentAdviceStatus.INITIAL) {
            changeStatus(PaymentAdviceStatus.INITIATED);
        } else {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "status",
                                    "Payment advice can't be initiated from status: " + status
                            )
            );
        }
    }

    public void rollbackFromInitiated() {
        if (PaymentAdviceStatus.INITIATED != status) {
            throw new IllegalStateException("Illegal status for rollback. Status: " + status);
        }

        if (BigDecimal.ZERO.compareTo(getTotalPaidAmount()) == 0) {
            changeStatus(PaymentAdviceStatus.INITIAL);
        } else {
            changeStatus(PaymentAdviceStatus.SPLIT_PAYMENT);
        }
    }

    public void pay() {
        checkStatusForPayment();

        payableLines.forEach(PayableLine::pay);

        afterPayment();
    }

    public void pay(BigDecimal amount) {
        checkStatusForPayment();

        if (getTotalToBePaidAmount().compareTo(amount) != 0) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "totalToBePaidAmount",
                                    "Payment amount didn't match to be paid amount. To be paid amount: " + getTotalToBePaidAmount() + ", payment amount: " + amount
                            )
            );
        }

        payableLines.forEach(PayableLine::pay);

        afterPayment();
    }

    public void pay(PaymentContext paymentContext) {

        checkStatusForPayment();

        paymentContext.getPayableLinePayments()
                .forEach(plc -> {
                    PayableLine payableLine = getRequiredPayableLineById(plc.getPayableLineId());

                    payableLine.pay(plc.getPaidAmount());
                        }
                );

        afterPayment();
    }

    public void upsertPaymentLine(PayableLine payableLine) {
        payableLines.
                stream()
                .filter(pl -> pl.getGlAccount().getCode().equals(payableLine.getGlAccount().getCode()))
                .findAny()
                .ifPresentOrElse(
                        pl -> pl.updateAmount(payableLine.getAmount()),
                        () -> payableLines.add(payableLine)
                );

        recalculateTotalLiabilityAmount();
        recalculateTotalPaidAmount();
        recalculateTotalToBePaidAmount();
    }


    public boolean isPaid() {
        return BigDecimal.ZERO.compareTo(getTotalToBePaidAmount()) == 0;
    }

    private void changeStatus(PaymentAdviceStatus status) {
        this.status = status;
    }

    private void checkStatusForPayment() {
        if (isPaid()) {
            throw new ViolationException(
                    new ValidationError()
                            .addViolation(
                                    "paymentAdviceId",
                                    "Payment advice by pan: [" + pan + "] has been already paid."
                            )
            );
        }
    }

    private void afterPayment() {
        recalculateTotalPaidAmount();
        recalculateTotalToBePaidAmount();

        if (isPaid()) {
            changeStatus(PaymentAdviceStatus.PAID);
        } else {
            changeStatus(PaymentAdviceStatus.SPLIT_PAYMENT);
        }
    }

    private void recalculateTotalLiabilityAmount() {
        totalLiabilityAmount = payableLines
                .stream()
                .map(PayableLine::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void recalculateTotalPaidAmount() {
        totalPaidAmount = payableLines
                .stream()
                .map(PayableLine::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void recalculateTotalToBePaidAmount() {
        totalToBePaidAmount = getTotalLiabilityAmount().subtract(getTotalPaidAmount());
    }

    @Data(staticConstructor = "of")
    public static class Period {
        private final String year;
        private final String segment;
    }

    public static PaymentAdvice withId(
            Long id,
            String drn,
            LocalDate dueDate,
            Period period,
            LocalDateTime creationDateTime,
            String pan,
            PaymentAdviceStatus status,
            EpTaxpayer taxpayer,
            PaymentAdviceBankInfo bankInfo,
            Collection<PayableLine> payableLines,
            BigDecimal totalLiabilityAmount,
            BigDecimal totalPaidAmountAmount,
            BigDecimal totalToBePaidAmount
    ) {
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
                new ArrayList<>(payableLines),
                totalLiabilityAmount,
                totalPaidAmountAmount,
                totalToBePaidAmount
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
                new ArrayList<>(payableLines),
                null,
                null,
                null
        );
    }

    @Data(staticConstructor = "of")
    public static class PaymentContext {
        private final Collection<PayableLinePaymentContext> payableLinePayments;
    }

    @Data(staticConstructor = "of")
    public static class PayableLinePaymentContext {
        private final Long payableLineId;
        private final BigDecimal paidAmount;
    }
}
