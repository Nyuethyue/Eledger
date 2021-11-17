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

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bfsc_code")
    private String bfscCode;


    @OneToMany(
            mappedBy = "refBankEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefBankDescriptionEntity> descriptions;

    public RefBankEntity(Long id, String bankName,String bfscCode) {
        this.id = id;
        this.bankName = bankName;
        this.bfscCode = bfscCode;
    }
    public void addToDescriptions(RefBankDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setRefBankEntity(this);
        descriptions.add(description);
    }
}
