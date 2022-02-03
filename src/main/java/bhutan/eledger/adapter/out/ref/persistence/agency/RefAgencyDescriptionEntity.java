package bhutan.eledger.adapter.out.ref.persistence.agency;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "agency_description", schema = "ref")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class RefAgencyDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "agency_description_id_seq", schema = "ref", sequenceName = "agency_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agency_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    private RefAgencyEntity agency;

    public RefAgencyDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }


    @Override
    public boolean idSupported() {
        return true;
    }
}
