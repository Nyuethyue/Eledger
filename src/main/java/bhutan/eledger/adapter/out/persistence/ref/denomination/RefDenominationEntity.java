package bhutan.eledger.adapter.out.persistence.ref.denomination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "denomination", schema = "ref")
@Getter
@Setter
class RefDenominationEntity {
    @Id
    @SequenceGenerator(name = "denomination_id_seq", schema = "ref", sequenceName = "denomination_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "denomination_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(
            mappedBy = "denomination",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefDenominationDescriptionEntity> descriptions;

    public RefDenominationEntity(Long id, String code, String symbol) {
        this.id = id;
        this.code = code;
        this.symbol = symbol;
    }

    public void addToDescriptions(RefDenominationDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setDenomination(this);
        descriptions.add(description);
    }


}
