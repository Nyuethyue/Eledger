package bhutan.eledger.domain.ref.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tax_period_segment", schema = "ref")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefTaxPeriodSegment {

    @Id
    @SequenceGenerator(name = "tax_period_segment_id_seq", schema = "ref", sequenceName = "tax_period_segment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_period_segment_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Transient
    private Multilingual description;

    @OneToMany(
            mappedBy = "taxPeriodSegment",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonIgnore
    private Collection<RefTaxPeriodSegmentDescription> descriptions;

    @Column(name = "tax_period_id")
    private Long taxPeriodTypeId;

    private RefTaxPeriodSegment(String code, Multilingual description, Long taxPeriodTypeId) {
        this.code = code;
        this.description = description;
        this.taxPeriodTypeId = taxPeriodTypeId;
    }

    protected Collection<RefTaxPeriodSegmentDescription> getDescriptions() {
        return descriptions;
    }

    @PostLoad
    private void initDescription() {
        description = Multilingual.of(getDescriptions());
    }

    public static RefTaxPeriodSegment withoutId(
            String code,
            Multilingual description,
            Long taxPeriodTypeId
    ) {
        return new RefTaxPeriodSegment(
                code,
                description,
                taxPeriodTypeId
        );
    }
}
