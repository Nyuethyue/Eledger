package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "TexPeriodConfig", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefTaxPeriodConfigEntity {
    @Id
    @SequenceGenerator(name = "bank_id_seq", schema = "ref", sequenceName = "bank_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "tax_type_id")
    private Long taxTypeId;

    @Column(name = "calendar_year")
    private Long calendarYear;


    @Column(name = "tax_period_type_id")
    private Long taxPeriodTypeId;

    @Column(name = "transaction_type_id")
    private Long transactionTypeId;

    @Column(name = "due_date_count_for_return_filing")
    private Long dueDateCountForReturnFiling;

    @Column(name = "due_date_count_for_payment")
    private Long dueDateCountForPayment;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "consider_non_working_days")
    private Boolean considerNonWorkingDays;

    @OneToMany(
            mappedBy = "TexPeriodConfig",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefTaxPeriodRecordEntity> records;

    public RefTaxPeriodConfigEntity(
            Long taxTypeId,
            Long calendarYear,
            Long taxPeriodTypeId,
            Long transactionTypeId,
            Long dueDateCountForReturnFiling,
            Long dueDateCountForPayment,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean considerNonWorkingDays
    ) {
        this.taxTypeId = taxTypeId;
        this.calendarYear = calendarYear;
        this.taxPeriodTypeId = taxPeriodTypeId;
        this.transactionTypeId = transactionTypeId;
        this.dueDateCountForReturnFiling = dueDateCountForReturnFiling;
        this.dueDateCountForPayment = dueDateCountForPayment;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.considerNonWorkingDays = considerNonWorkingDays;
    }

    public void addToRecords(RefTaxPeriodRecordEntity record) {
        if (record == null) {
            records = new HashSet<>();
        }

        records.add(record);
    }
}
