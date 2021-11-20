package bhutan.eledger.adapter.persistence.ref.bank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "bank", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefBankEntity {
    @Id
    @SequenceGenerator(name = "bank_id_seq", schema = "ref", sequenceName = "bank_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;


    @OneToMany(
            mappedBy = "bank",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefBankDescriptionEntity> descriptions;

    public RefBankEntity(Long id,String code) {
        this.id = id;
        this.code = code;
    }

    public void addToDescriptions(RefBankDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setBank(this);
        descriptions.add(description);
    }
}
