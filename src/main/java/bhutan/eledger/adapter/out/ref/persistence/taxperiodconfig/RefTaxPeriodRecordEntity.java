package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "TexPeriodRecord", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefTaxPeriodRecordEntity {
    @Id
    @SequenceGenerator(name = "bank_id_seq", schema = "ref", sequenceName = "bank_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_id_seq")
    @Column(name = "id")
    private Long id;

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

    @Column(name = "remark")
    private String remark;


    public RefTaxPeriodRecordEntity(
            Long id,
            LocalDate periodStartDate,
            LocalDate periodEndDate,
            LocalDate filingDueDate,
            LocalDate paymentDueDate,
            LocalDate interestCalcStartDay,
            LocalDate fineAndPenaltyCalcStartDay,
            LocalDate validFrom,
            String remark
    ) {
        this.id = id;
        this.periodStartDate = periodStartDate;
        this.periodEndDate = periodEndDate;
        this.filingDueDate = filingDueDate;
        this.paymentDueDate = paymentDueDate;
        this.interestCalcStartDay = interestCalcStartDay;
        this.fineAndPenaltyCalcStartDay = fineAndPenaltyCalcStartDay;
        this.validFrom = validFrom;
        this.remark = remark;
    }
}
