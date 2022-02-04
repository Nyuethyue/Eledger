package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "open_close_tax_period_config", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefOpenCloseTaxPeriodEntity {
    @Id
    @SequenceGenerator(name = "open_close_tax_period_config_id_seq", schema = "ref", sequenceName = "open_close_tax_period_config_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "open_close_tax_period_config_id_seq")
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

    @Column(name = "years")
    private int years;

    @Column(name = "month")
    private int month;

    @OneToMany(
            mappedBy = "openCloseTaxPeriodEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefOpenCloseTaxPeriodRecordEntity> records;

    public RefOpenCloseTaxPeriodEntity(
            String glAccountPartFullCode,
            Integer calendarYear,
            Long taxPeriodTypeId,
            Long transactionTypeId,
            Integer years,
            Integer month
    ) {
        this.glAccountPartFullCode = glAccountPartFullCode;
        this.calendarYear = calendarYear;
        this.taxPeriodTypeId = taxPeriodTypeId;
        this.transactionTypeId = transactionTypeId;
        this.years = years;
        this.month = month;
    }

    public void addToRecords(RefOpenCloseTaxPeriodRecordEntity record) {
        if (records == null) {
            records = new HashSet<>();
        }

        record.setOpenCloseTaxPeriodEntity(this);
        records.add(record);
    }
}
