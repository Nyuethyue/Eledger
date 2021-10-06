package bhutan.eledger.adapter.persistence.config.transaction;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "transaction_type_attribute", schema = "config")
@AllArgsConstructor
@NoArgsConstructor
class TransactionTypeAttributeEntity {

    @Id
    @SequenceGenerator(name = "transaction_type_attribute_id_seq", schema = "config", sequenceName = "transaction_type_attribute_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_type_attribute_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "data_type_id")
    private Integer dataTypeId;

    @OneToMany(
            mappedBy = "transactionTypeAttribute",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<TransactionTypeAttributeDescriptionEntity> descriptions;

    public TransactionTypeAttributeEntity(Long id, String name, Integer dataTypeId) {
        this.id = id;
        this.name = name;
        this.dataTypeId = dataTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(Integer dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public Set<TransactionTypeAttributeDescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Set<TransactionTypeAttributeDescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }
}
