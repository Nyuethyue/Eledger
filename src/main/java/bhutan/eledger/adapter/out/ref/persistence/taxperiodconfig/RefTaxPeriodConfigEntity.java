package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tax_period_config", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefTaxPeriodConfigEntity {
    @Id
    @SequenceGenerator(name = "tax_period_config_id_seq", schema = "ref", sequenceName = "tax_period_config_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_period_config_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "gl_account_part_full_code")
    private String glAccountPartFullCode;// taxTypeCode;

    @Column(name = "calendar_year")
    private int calendarYear;

    @Column(name = "tax_period_type_code")
    private String taxPeriodTypeCode;

    @Column(name = "transaction_type_id")
    private long transactionTypeId;

    @Column(name = "due_date_count_for_return_filing")
    private int dueDateCountForReturnFiling;

    @Column(name = "due_date_count_for_payment")
    private int dueDateCountForPayment;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "consider_non_working_days")
    private Boolean considerNonWorkingDays;


    public static RefTaxPeriodConfigEntity withoutId(
            String glAccountPartFullCode,
            Integer calendarYear,
            String taxPeriodTypeCode,
            Long transactionTypeId,
            Integer dueDateCountForReturnFiling,
            Integer dueDateCountForPayment,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean considerNonWorkingDays
    ) {
        return new RefTaxPeriodConfigEntity(
                null,
                glAccountPartFullCode,
                calendarYear,
                taxPeriodTypeCode,
                transactionTypeId,
                dueDateCountForReturnFiling,
                dueDateCountForPayment,
                validFrom,
                validTo,
                considerNonWorkingDays
                );
    }

    public static RefTaxPeriodConfigEntity withId(
            long id,
            String glAccountPartFullCode,
            Integer calendarYear,
            String taxPeriodTypeCode,
            Long transactionTypeId,
            Integer dueDateCountForReturnFiling,
            Integer dueDateCountForPayment,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean considerNonWorkingDays
    ) {
        return new RefTaxPeriodConfigEntity(
                id,
                glAccountPartFullCode,
                calendarYear,
                taxPeriodTypeCode,
                transactionTypeId,
                dueDateCountForReturnFiling,
                dueDateCountForPayment,
                validFrom,
                validTo,
                considerNonWorkingDays
                );
    }

    public RefTaxPeriodConfigEntity(
            Long id,
            String glAccountPartFullCode,
            Integer calendarYear,
            String taxPeriodTypeCode,
            Long transactionTypeId,
            Integer dueDateCountForReturnFiling,
            Integer dueDateCountForPayment,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean considerNonWorkingDays
    ) {
        this.id = id;
        this.glAccountPartFullCode = glAccountPartFullCode;
        this.calendarYear = calendarYear;
        this.taxPeriodTypeCode = taxPeriodTypeCode;
        this.transactionTypeId = transactionTypeId;
        this.dueDateCountForReturnFiling = dueDateCountForReturnFiling;
        this.dueDateCountForPayment = dueDateCountForPayment;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.considerNonWorkingDays = considerNonWorkingDays;
    }
}
