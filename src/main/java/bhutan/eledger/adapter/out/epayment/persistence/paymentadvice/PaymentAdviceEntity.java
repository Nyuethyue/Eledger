package bhutan.eledger.adapter.out.epayment.persistence.paymentadvice;

import bhutan.eledger.domain.epayment.taxpayer.EpTaxpayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ep_payment_advice", schema = "epayment")
@Getter
@Setter
class PaymentAdviceEntity {
    @Id
    @SequenceGenerator(name = "ep_payment_advice_id_seq", sequenceName = "ep_payment_advice_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_payment_advice_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "drn")
    private String drn;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "period_year")
    private String periodYear;

    @Column(name = "period_segment")
    private String periodSegment;

    @Column(name = "creation_date_time", nullable = false, updatable = false)
    private LocalDateTime creationDateTime;

    @Column(name = "pan")
    private String pan;

    @Column(name = "status")
    private String status;

    @Column(name = "total_liability_amount")
    private BigDecimal totalLiabilityAmount;
    @Column(name = "total_paid_amount")
    private BigDecimal totalPaidAmount;
    @Column(name = "total_to_be_paid_amount")
    private BigDecimal totalToBePaidAmount;

    @OneToOne
    @JoinColumn(name = "taxpayer_id", nullable = false)
    private EpTaxpayer taxpayer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pa_bank_info_id", nullable = false)
    private PaymentAdviceBankInfoEntity bankInfo;

    @OneToMany(
            mappedBy = "paymentAdvice",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<PayableLineEntity> payableLines;

    public PaymentAdviceEntity(Long id, String drn, LocalDate dueDate, String periodYear, String periodSegment, LocalDateTime creationDateTime, String pan, String status, BigDecimal totalLiabilityAmount, BigDecimal totalPaidAmount, BigDecimal totalToBePaidAmount, EpTaxpayer taxpayer, PaymentAdviceBankInfoEntity bankInfo) {
        this.id = id;
        this.drn = drn;
        this.dueDate = dueDate;
        this.periodYear = periodYear;
        this.periodSegment = periodSegment;
        this.creationDateTime = creationDateTime;
        this.pan = pan;
        this.status = status;
        this.taxpayer = taxpayer;
        this.bankInfo = bankInfo;
        this.totalLiabilityAmount = totalLiabilityAmount;
        this.totalPaidAmount = totalPaidAmount;
        this.totalToBePaidAmount = totalToBePaidAmount;
    }
}
