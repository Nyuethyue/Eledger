package bhutan.eledger.adapter.persistence.ref.currency;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "currency_description", schema = "ref")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class RefCurrencyDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "currency_description_id_seq", schema = "ref", sequenceName = "currency_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private RefCurrencyEntity currency;

    public RefCurrencyDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
