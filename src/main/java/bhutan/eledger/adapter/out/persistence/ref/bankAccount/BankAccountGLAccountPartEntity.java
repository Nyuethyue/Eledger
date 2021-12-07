package bhutan.eledger.adapter.out.persistence.ref.bankAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "bank_account_gl_account_part", schema = "ref")
@NoArgsConstructor
@Getter
@Setter
class BankAccountGLAccountPartEntity {
    @Id
    @SequenceGenerator(name = "bank_account_gl_account_part_id_seq", schema = "ref", sequenceName = "bank_account_gl_account_part_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_gl_account_part_id_seq")
    @Column(name = "id")
    private Long id;

    //the full code of gl part
    @Column(name = "code")
    private String code;

    public BankAccountGLAccountPartEntity(Long id, String code) {
        this.id = id;
        this.code = code;
    }
}
