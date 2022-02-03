package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


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

    @Column(name = "tax_period_type_id")
    private long taxPeriodTypeId;

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

    @OneToMany(
            mappedBy = "taxPeriodConfig",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefTaxPeriodRecordEntity> records;

    public RefTaxPeriodConfigEntity(
            String glAccountPartFullCode,
            Integer calendarYear,
            Long taxPeriodTypeId,
            Long transactionTypeId,
            Integer dueDateCountForReturnFiling,
            Integer dueDateCountForPayment,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean considerNonWorkingDays
    ) {
        this.glAccountPartFullCode = glAccountPartFullCode;
        this.calendarYear = calendarYear;
        this.taxPeriodTypeId = taxPeriodTypeId;
        this.transactionTypeId = transactionTypeId;
        this.dueDateCountForReturnFiling = dueDateCountForReturnFiling;
        this.dueDateCountForPayment = dueDateCountForPayment;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.considerNonWorkingDays = considerNonWorkingDays;
    }

    public void addToRecord(RefTaxPeriodRecordEntity record) {
        if (records == null) {
            records = new HashSet<>();
        }

        records.add(record);
    }
}
