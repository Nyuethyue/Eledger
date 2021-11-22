package bhutan.eledger.adapter.out.persistence.ref.paymentmode;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "payment_mode_description", schema = "ref")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class PaymentModeDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "payment_mode_description_id_seq", schema = "ref", sequenceName = "payment_mode_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_mode_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "payment_mode_id", nullable = false)
    private PaymentModeDescriptionEntity paymentMode;

    public PaymentModeDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }
    @Override
    public boolean idSupported() {
        return true;
    }


}
