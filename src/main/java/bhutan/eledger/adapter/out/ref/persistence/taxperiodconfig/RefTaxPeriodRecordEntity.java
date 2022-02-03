package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "ref_tax_period_record", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefTaxPeriodRecordEntity {
    @Id
    @SequenceGenerator(name = "ref_tax_period_record_id_seq", schema = "ref", sequenceName = "ref_tax_period_record_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ref_tax_period_record_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "period_id")
    private int periodId;

    @Column(name = "period_start_date")
    private LocalDate periodStartDate;

    @Column(name = "period_end_date")
    private LocalDate periodEndDate;

    @Column(name = "filing_due_date")
    private LocalDate filingDueDate;

    @Column(name = "payment_due_date")
    private LocalDate paymentDueDate;

    @Column(name = "interest_calc_start_day")
    private LocalDate interestCalcStartDay;

    @Column(name = "fine_and_penalty_calc_start_day")
    private LocalDate fineAndPenaltyCalcStartDay;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "tax_type_code")
    private String taxTypeCode;

    @ManyToOne
    @JoinColumn(name = "tax_period_config_id", nullable = false)
    private RefTaxPeriodConfigEntity taxPeriodConfig;


    public RefTaxPeriodRecordEntity(
            Long id,
            LocalDate periodStartDate,
            LocalDate periodEndDate,
            LocalDate filingDueDate,
            LocalDate paymentDueDate,
            LocalDate interestCalcStartDay,
            LocalDate fineAndPenaltyCalcStartDay,
            LocalDate validFrom,
            String taxTypeCode
    ) {
        this.id = id;
        this.periodStartDate = periodStartDate;
        this.periodEndDate = periodEndDate;
        this.filingDueDate = filingDueDate;
        this.paymentDueDate = paymentDueDate;
        this.interestCalcStartDay = interestCalcStartDay;
        this.fineAndPenaltyCalcStartDay = fineAndPenaltyCalcStartDay;
        this.validFrom = validFrom;
        this.taxTypeCode = taxTypeCode;
    }
}
