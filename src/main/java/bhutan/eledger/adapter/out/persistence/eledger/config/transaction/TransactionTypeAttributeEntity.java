package bhutan.eledger.adapter.out.persistence.eledger.config.transaction;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "el_transaction_type_attribute", schema = "eledger_config")
@AllArgsConstructor
@NoArgsConstructor
class TransactionTypeAttributeEntity {

    @Id
    @SequenceGenerator(name = "el_transaction_type_attribute_id_seq", schema = "eledger_config", sequenceName = "el_transaction_type_attribute_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_transaction_type_attribute_id_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "data_type_id")
    private Integer dataTypeId;

    @OneToMany(
            mappedBy = "transactionTypeAttribute",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<TransactionTypeAttributeDescriptionEntity> descriptions;

    public TransactionTypeAttributeEntity(Long id, String code, Integer dataTypeId) {
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

    public void setCode(String code) {
        this.code = code;
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
