package bhutan.eledger.adapter.out.ref.persistence.taxperiodconfig;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "open_close_tax_period_config_record", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefOpenCloseTaxPeriodRecordEntity {
    @Id
    @SequenceGenerator(name = "open_close_tax_period_config_record_id_seq", schema = "ref", sequenceName = "open_close_tax_period_config_record_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "open_close_tax_period_config_record_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "period_id")
    private int periodId;

    @Column(name = "period")
    private String period;

    @Column(name = "period_start_date")
    private LocalDate periodStartDate;

    @Column(name = "period_end_date")
    private LocalDate periodEndDate;


    @ManyToOne
    @JoinColumn(name = "open_close_tax_period_config_id", nullable = false)
    private RefOpenCloseTaxPeriodEntity openCloseTaxPeriodEntity;

    public RefOpenCloseTaxPeriodRecordEntity(
            Long id,
            Integer periodId,
            String period,
            LocalDate periodStartDate,
            LocalDate periodEndDate
    ) {
        this.id = id;
        this.periodId = periodId;
        this.period = period;
        this.periodStartDate = periodStartDate;
        this.periodEndDate = periodEndDate;
    }
}
