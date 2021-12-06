package bhutan.eledger.adapter.out.persistence.ref.bankAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "code")
    private String code;

    @Column(name = "start_of_validity")
    private LocalDate startOfValidity;

    @Column(name = "end_of_validity")
    private LocalDate endOfValidity;

    @Column(name = "is_primary_gl_account")
    private Boolean isPrimaryForGlAccount;

    @OneToMany(
            mappedBy = "bankAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<RefBankAccountDescriptionEntity> descriptions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_gl_account_part_id", nullable = false)
    private BankAccountGLAccountPartEntity bankAccountGLAccountPart;


    public RefBankAccountEntity(Long id, Long branchId, String code,
                                LocalDate startOfValidity, LocalDate endOfValidity,
                                Boolean isPrimaryForGlAccount,
                                BankAccountGLAccountPartEntity bankAccountGLAccountPart) {
        this.id = id;
        this.branchId = branchId;
        this.code = code;
        this.startOfValidity = startOfValidity;
        this.endOfValidity = endOfValidity;
        this.isPrimaryForGlAccount = isPrimaryForGlAccount;
        this.bankAccountGLAccountPart = bankAccountGLAccountPart;

    }

    public void addToDescriptions(RefBankAccountDescriptionEntity description) {
        if (descriptions == null) {
            descriptions = new HashSet<>();
        }

        description.setBankAccount(this);
        descriptions.add(description);
    }
}
