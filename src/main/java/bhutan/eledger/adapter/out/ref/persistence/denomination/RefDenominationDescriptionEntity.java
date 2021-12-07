package bhutan.eledger.adapter.out.ref.persistence.denomination;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "denomination_description", schema = "ref")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class RefDenominationDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "denomination_description_id_seq", schema = "ref", sequenceName = "denomination_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "denomination_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "denomination_id", nullable = false)
    private RefDenominationEntity denomination;

    public RefDenominationDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
