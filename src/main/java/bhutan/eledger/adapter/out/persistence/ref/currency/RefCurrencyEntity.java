package bhutan.eledger.adapter.out.persistence.ref.currency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "currency", schema = "ref")
@Getter
@Setter
class RefCurrencyEntity {
    @Id
    @SequenceGenerator(name = "currency_id_seq", schema = "ref", sequenceName = "currency_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(
            mappedBy = "currency",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefCurrencyDescriptionEntity> descriptions;

    public RefCurrencyEntity(Long id, String code, String symbol) {
        this.id = id;
        this.code = code;
        this.symbol = symbol;
    }

    public void addToDescriptions(RefCurrencyDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setCurrency(this);
        descriptions.add(description);
    }


}
