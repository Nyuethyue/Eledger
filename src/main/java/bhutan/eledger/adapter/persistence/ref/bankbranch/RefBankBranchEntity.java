package bhutan.eledger.adapter.persistence.ref.bankbranch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank_branch", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefBankBranchEntity {
    @Id
    @SequenceGenerator(name = "bank_branch_id_seq", schema = "ref", sequenceName = "bank_branch_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_branch_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "bfsc_code")
    private String bfscCode;

    @Column(name = "address")
    private String address;

    @Column(name = "bank_id")
    private Long bankId;

    @OneToMany(
            mappedBy = "bankBranch",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefBankBranchDescriptionEntity> descriptions;

    public RefBankBranchEntity(Long id, String code,String bfscCode,
                               String address,Long bankId) {
        this.id = id;
        this.code = code;
        this.bfscCode = bfscCode;
        this.address = address;
        this.bankId = bankId;
    }
    public void addToDescriptions(RefBankBranchDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setBankBranch(this);
        descriptions.add(description);
    }
}
