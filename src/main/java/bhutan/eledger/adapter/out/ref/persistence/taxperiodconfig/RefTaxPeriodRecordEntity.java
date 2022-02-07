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

    @Column(name = "calendar_year")
    private int year;

    @Column(name = "period_start_month")
    private int periodStartMonth;
    @Column(name = "period_start_day")
    private int periodStartDay;


    @Column(name = "period_end_month")
    private int periodEndMonth;
    @Column(name = "period_end_day")
    private int periodEndDay;

    @Column(name = "payment_due_month")
    private int paymentDueMonth;
    @Column(name = "payment_due_day")
    private int paymentDueDay;


    @Column(name = "filing_due_month")
    private int filingDueMonth;
    @Column(name = "filing_due_day")
    private int filingDueDay;


    @Column(name = "interest_calc_start_month")
    private int interestCalcStartMonth;
    @Column(name = "interest_calc_start_day")
    private int interestCalcStartDay;


    @Column(name = "fine_and_penalty_calc_start_month")
    private int fineAndPenaltyCalcStartMonth;
    @Column(name = "fine_and_penalty_calc_start_day")
    private int fineAndPenaltyCalcStartDay;


    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "tax_type_code")
    private String taxTypeCode;

    @Column(name = "tax_period_config_id")
    private Long taxPeriodConfigId;

    public RefTaxPeriodRecordEntity(
            Long taxPeriodConfigId,
            Integer periodId,

            int year,

            int periodStartMonth,
            int periodStartDay,

            int periodEndMonth,
            int periodEndDay,

            int filingDueMonth,
            int filingDueDay,

            int paymentDueMonth,
            int paymentDueDay,

            int interestCalcStartMonth,
            int interestCalcStartDay,

            int fineAndPenaltyCalcStartMonth,
            int fineAndPenaltyCalcStartDay,

            LocalDate validFrom,
            String taxTypeCode
    ) {
        this.taxPeriodConfigId = taxPeriodConfigId;
        this.periodId = periodId;

        this.year = year;

        this.periodStartMonth = periodStartMonth;
        this.periodStartDay = periodStartDay;

        this.periodEndMonth = periodEndMonth;
        this.periodEndDay = periodEndDay;

        this.filingDueMonth = filingDueMonth;
        this.filingDueDay = filingDueDay;

        this.paymentDueMonth = paymentDueMonth;
        this.paymentDueDay = paymentDueDay;

        this.interestCalcStartMonth = interestCalcStartMonth;
        this.interestCalcStartDay = interestCalcStartDay;

        this.fineAndPenaltyCalcStartMonth = fineAndPenaltyCalcStartMonth;
        this.fineAndPenaltyCalcStartDay = fineAndPenaltyCalcStartDay;
        this.validFrom = validFrom;
        this.taxTypeCode = taxTypeCode;
    }
}
