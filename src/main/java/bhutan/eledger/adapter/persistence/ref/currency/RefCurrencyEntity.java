package bhutan.eledger.adapter.persistence.ref.currency;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(
            mappedBy = "refCurrency",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefCurrencyDescriptionEntity> descriptions;

    public RefCurrencyEntity(Long id, String code) {
        this.id = id;
        this.code = code;
    }


}
