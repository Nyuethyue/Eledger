package bhutan.eledger.adapter.out.persistence.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ep_pa_gl_account_description", schema = "epayment")
@NoArgsConstructor
class PaymentAdviceGLAccountDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "ep_pa_gl_account_description_id_seq", sequenceName = "ep_pa_gl_account_description_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_pa_gl_account_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pa_gl_account_id", nullable = false)
    private PaymentAdviceGLAccountEntity glAccount;

    public PaymentAdviceGLAccountDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    public PaymentAdviceGLAccountDescriptionEntity(Long id, String languageCode, String value, PaymentAdviceGLAccountEntity glAccount) {
        super(languageCode, value);
        this.id = id;
        this.glAccount = glAccount;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentAdviceGLAccountEntity getGlAccount() {
        return glAccount;
    }

    public void setGlAccount(PaymentAdviceGLAccountEntity glAccount) {
        this.glAccount = glAccount;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
