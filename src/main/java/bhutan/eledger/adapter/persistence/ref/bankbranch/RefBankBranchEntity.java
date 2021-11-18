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

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_bfsc_code")
    private String branchBfscCode;

    @Column(name = "location")
    private String location;

    @Column(name = "bank_id")
    private Long bankId;

    @OneToMany(
            mappedBy = "refBankBranch",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefBankBranchDescriptionEntity> descriptions;

    public RefBankBranchEntity(Long id, String branchCode,String branchName,String branchBfscCode,
                               String location,Long bankId) {
        this.id = id;
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.branchBfscCode = branchBfscCode;
        this.location = location;
        this.bankId = bankId;
    }
    public void addToDescriptions(RefBankBranchDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setRefBankBranch(this);
        descriptions.add(description);
    }
}
