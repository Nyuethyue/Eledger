package bhutan.eledger.adapter.persistence.epayment.paymentadvice;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ep_pa_bank_info_description", schema = "epayment")
@NoArgsConstructor
class PaymentAdviceBankInfoDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "ep_pa_bank_info_description_id_seq", sequenceName = "ep_pa_bank_info_description_id_seq", schema = "epayment", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ep_pa_bank_info_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pa_bank_info_id", nullable = false)
    private PaymentAdviceBankInfoEntity bankInfo;

    public PaymentAdviceBankInfoDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    public PaymentAdviceBankInfoDescriptionEntity(Long id, String languageCode, String value, PaymentAdviceBankInfoEntity bankInfo) {
        super(languageCode, value);
        this.id = id;
        this.bankInfo = bankInfo;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentAdviceBankInfoEntity getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(PaymentAdviceBankInfoEntity bankInfo) {
        this.bankInfo = bankInfo;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
