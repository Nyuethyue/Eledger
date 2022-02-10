package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tax_period_config_record", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefTaxPeriodRecordEntity {
    @Id
    @SequenceGenerator(name = "tax_period_config_record_id_seq", schema = "ref", sequenceName = "tax_period_config_record_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_period_config_record_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "period_segment_id")
    private long periodSegmentId;

    @Column(name = "period_start_date")
    private LocalDate periodStartDate;

    @Column(name = "period_end_date")
    private LocalDate periodEndDate;

    @Column(name = "filing_due_date")
    private LocalDate filingDueDate;

    @Column(name = "payment_due_date")
    private LocalDate paymentDueDate;

    @Column(name = "interest_calc_start_date")
    private LocalDate interestCalcStartDate;

    @Column(name = "fine_and_penalty_calc_start_date")
    private LocalDate fineAndPenaltyCalcStartDate;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "tax_type_code")
    private String taxTypeCode;

    @Column(name = "tax_period_config_id")
    private Long taxPeriodConfigId;

    public RefTaxPeriodRecordEntity(
            Long taxPeriodConfigId,
            Long periodSegmentId,
            LocalDate periodStartDate,
            LocalDate periodEndDate,
            LocalDate filingDueDate,
            LocalDate paymentDueDate,
            LocalDate interestCalcStartDate,
            LocalDate fineAndPenaltyCalcStartDate,
            LocalDate validFrom,
            String taxTypeCode
    ) {
        this.taxPeriodConfigId = taxPeriodConfigId;
        this.periodSegmentId = periodSegmentId;

        this.periodStartDate = periodStartDate;
        this.periodEndDate = periodEndDate;
        this.filingDueDate = filingDueDate;
        this.paymentDueDate = paymentDueDate;
        this.interestCalcStartDate = interestCalcStartDate;
        this.fineAndPenaltyCalcStartDate = fineAndPenaltyCalcStartDate;
        this.validFrom = validFrom;
        this.taxTypeCode = taxTypeCode;
    }
}
