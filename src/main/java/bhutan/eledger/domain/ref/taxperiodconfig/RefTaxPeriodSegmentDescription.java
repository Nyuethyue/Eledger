package bhutan.eledger.domain.ref.taxperiodconfig;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tax_period_segment_description", schema = "ref")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
class RefTaxPeriodSegmentDescription extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "tax_period_segment_description_id_seq", sequenceName = "tax_period_segment_description_id_seq", schema = "ref", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_period_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tax_period_segment_id", nullable = false)
    private RefTaxPeriodSegment taxPeriodSegment;

    RefTaxPeriodSegmentDescription(Long id, String languageCode, String value, RefTaxPeriodSegment taxPeriodSegment) {
        super(languageCode, value);
        this.id = id;
        this.taxPeriodSegment = taxPeriodSegment;
    }

    @Override
    public boolean idSupported() {
        return true;
    }

}
