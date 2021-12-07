package bhutan.eledger.adapter.out.eledger.persistence.config.property;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "value")
    private String value;

    @Column(name = "start_of_validity")
    private LocalDate startOfValidity;

    @Column(name = "end_of_validity")
    private LocalDate endOfValidity;

    @OneToMany(
            mappedBy = "property",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<PropertyDescriptionEntity> descriptions;

    public PropertyEntity(Long id, String code, Integer dataTypeId, String value, LocalDate startOfValidity, LocalDate endOfValidity) {
        this.id = id;
        this.code = code;
        this.dataTypeId = dataTypeId;
        this.value = value;
        this.startOfValidity = startOfValidity;
        this.endOfValidity = endOfValidity;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getStartOfValidity() {
        return startOfValidity;
    }

    public void setStartOfValidity(LocalDate startOfValidity) {
        this.startOfValidity = startOfValidity;
    }

    public LocalDate getEndOfValidity() {
        return endOfValidity;
    }

    public void setEndOfValidity(LocalDate endOfValidity) {
        this.endOfValidity = endOfValidity;
    }

    public Set<PropertyDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<PropertyDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }
}
