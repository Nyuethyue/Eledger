package bhutan.eledger.adapter.persistence.epayment.paymentadvice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pa_gl_account", schema = "epayment")
@Getter
@Setter
class PaymentAdviceGLAccountEntity {
    @Id
    @SequenceGenerator(name = "pa_gl_account_id_seq", sequenceName = "pa_gl_account_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pa_gl_account_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @OneToMany(
            mappedBy = "glAccount",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<PaymentAdviceGLAccountDescriptionEntity> descriptions;

    public PaymentAdviceGLAccountEntity(Long id, String code) {
        this.id = id;
        this.code = code;
    }
}
