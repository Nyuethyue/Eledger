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
    private Integer calendarYear;

    @Column(name = "tax_period_code")
    private String taxPeriodCode;

    @Column(name = "transaction_type_id")
    private long transactionTypeId;

    @Column(name = "no_of_years")
    private Integer yearsNo;

    @Column(name = "no_of_month")
    private Integer month;

    @OneToMany(
            mappedBy = "openCloseTaxPeriodEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefOpenCloseTaxPeriodRecordEntity> records;

    public RefOpenCloseTaxPeriodEntity(
            Long id,
            String glAccountPartFullCode,
            Integer calendarYear,
            String taxPeriodCode,
            Long transactionTypeId,
            Integer yearsNo,
            Integer month
    ) {
        this.id = id;
        this.glAccountPartFullCode = glAccountPartFullCode;
        this.calendarYear = calendarYear;
        this.taxPeriodCode = taxPeriodCode;
        this.transactionTypeId = transactionTypeId;
        this.yearsNo = yearsNo;
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
