package bhutan.eledger.adapter.persistence.eledger.config.property;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "el_property_description", schema = "eledger_config")
@NoArgsConstructor
class PropertyDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "el_property_description_id_seq", schema = "eledger_config", sequenceName = "el_property_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_property_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private PropertyEntity property;

    public PropertyDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    public PropertyDescriptionEntity(Long id, String languageCode, String value, PropertyEntity property) {
        super(languageCode, value);
        this.id = id;
        this.property = property;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity propertyEntity) {
        this.property = propertyEntity;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
