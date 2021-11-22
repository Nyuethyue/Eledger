package bhutan.eledger.adapter.out.persistence.eledger.config.transaction;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "el_transaction_type_transaction_type_attribute", schema = "eledger_config")
@NoArgsConstructor
class TransactionTypeTransactionTypeAttributeEntity {
    @EmbeddedId
    private TransactionTypeTransactionTypeAttrId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("transactionTypeId")
    private TransactionTypeEntity transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("transactionTypeAttributeId")
    private TransactionTypeAttributeEntity transactionTypeAttribute;

    public TransactionTypeTransactionTypeAttributeEntity(TransactionTypeEntity transactionType, TransactionTypeAttributeEntity transactionTypeAttribute) {
        this.transactionType = transactionType;
        this.transactionTypeAttribute = transactionTypeAttribute;
        this.id = new TransactionTypeTransactionTypeAttrId(transactionType.getId(), transactionTypeAttribute.getId());
    }

    public TransactionTypeTransactionTypeAttrId getId() {
        return id;
    }

    public void setId(TransactionTypeTransactionTypeAttrId id) {
        this.id = id;
    }

    public TransactionTypeEntity getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEntity transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionTypeAttributeEntity getTransactionTypeAttribute() {
        return transactionTypeAttribute;
    }

    public void setTransactionTypeAttribute(TransactionTypeAttributeEntity transactionTypeAttribute) {
        this.transactionTypeAttribute = transactionTypeAttribute;
    }
}
