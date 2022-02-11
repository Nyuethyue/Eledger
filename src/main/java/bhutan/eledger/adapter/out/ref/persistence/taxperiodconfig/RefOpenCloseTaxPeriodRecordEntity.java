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

    @Column(name = "period_segment_id")
    private Long periodSegmentId;

    @Column(name = "period_open_date")
    private LocalDate periodOpenDate;

    @Column(name = "period_close_date")
    private LocalDate periodCloseDate;


    @ManyToOne
    @JoinColumn(name = "open_close_tax_period_config_id", nullable = false)
    private RefOpenCloseTaxPeriodEntity openCloseTaxPeriodEntity;

    public RefOpenCloseTaxPeriodRecordEntity(
            Long id,
            Long periodSegmentId,
            LocalDate periodOpenDate,
            LocalDate periodCloseDate
    ) {
        this.id = id;
        this.periodSegmentId = periodSegmentId;
        this.periodOpenDate = periodOpenDate;
        this.periodCloseDate = periodCloseDate;
    }
}
