package bhutan.eledger.adapter.out.persistence.ref.bankAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank_account", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class RefBankAccountEntity {
    @Id
    @SequenceGenerator(name = "bank_account_id_seq", schema = "ref", sequenceName = "bank_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "branch_id")
    private Long branchId;

    @Column(name = "acc_number")
    private String accNumber;

    @OneToMany(
            mappedBy = "bankAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefBankAccountDescriptionEntity> descriptions;

    public RefBankAccountEntity(Long id, Long branchId,String accNumber) {
        this.id = id;
        this.branchId = branchId;
        this.accNumber = accNumber;
    }
    public void addToDescriptions(RefBankAccountDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setBankAccount(this);
        descriptions.add(description);
    }
}
