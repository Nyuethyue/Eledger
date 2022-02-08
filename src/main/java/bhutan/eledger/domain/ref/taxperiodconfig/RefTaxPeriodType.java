package bhutan.eledger.domain.ref.taxperiodconfig;

import am.iunetworks.lib.multilingual.core.Multilingual;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "tax_period", schema = "ref")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefTaxPeriodType {

    @Id
    @SequenceGenerator(name = "tax_period_id_seq", schema = "ref", sequenceName = "tax_period_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tax_period_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Transient
    private Multilingual description;

    @OneToMany(
            mappedBy = "taxPeriodType",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonIgnore
    private Collection<RefTaxPeriodTypeDescription> descriptions;

    private RefTaxPeriodType(String code, Multilingual description) {
        this.code = code;
        this.description = description;
    }

    protected Collection<RefTaxPeriodTypeDescription> getDescriptions() {
        return descriptions;
    }

    @PostLoad
    private void initDescription() {
        description = Multilingual.of(getDescriptions());
    }

    public static RefTaxPeriodType withoutId(
            String code,
            Multilingual description
    ) {
        return new RefTaxPeriodType(
                code,
                description
        );
    }
}
