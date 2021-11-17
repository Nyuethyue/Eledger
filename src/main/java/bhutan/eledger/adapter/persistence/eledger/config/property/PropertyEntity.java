package bhutan.eledger.adapter.persistence.eledger.config.property;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "el_property", schema = "eledger_config")
@AllArgsConstructor
@NoArgsConstructor
class PropertyEntity {

    @Id
    @SequenceGenerator(name = "el_property_id_seq", schema = "eledger_config", sequenceName = "el_property_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_property_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "data_type_id")
    private Integer dataTypeId;

    @OneToMany(
            mappedBy = "property",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<PropertyDescriptionEntity> descriptions;

    public PropertyEntity(Long id, String code, Integer dataTypeId) {
        this.id = id;
        this.code = code;
        this.dataTypeId = dataTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String name) {
        this.code = name;
    }

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public Set<PropertyDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<PropertyDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }
}
