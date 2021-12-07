package bhutan.eledger.adapter.out.eledger.persistence.config.transaction;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "el_transaction_type_attribute_description", schema = "eledger_config")
@NoArgsConstructor
class TransactionTypeAttributeDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "el_transaction_type_attribute_description_id_seq", schema = "eledger_config", sequenceName = "el_transaction_type_attribute_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "el_transaction_type_attribute_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_type_attribute_id", nullable = false)
    private TransactionTypeAttributeEntity transactionTypeAttribute;

    public TransactionTypeAttributeDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    public TransactionTypeAttributeDescriptionEntity(Long id, String languageCode, String value, TransactionTypeAttributeEntity transactionTypeAttribute) {
        super(languageCode, value);
        this.id = id;
        this.transactionTypeAttribute = transactionTypeAttribute;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionTypeAttributeEntity getTransactionTypeAttribute() {
        return transactionTypeAttribute;
    }

    public void setTransactionTypeAttribute(TransactionTypeAttributeEntity transactionTypeAttribute) {
        this.transactionTypeAttribute = transactionTypeAttribute;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
