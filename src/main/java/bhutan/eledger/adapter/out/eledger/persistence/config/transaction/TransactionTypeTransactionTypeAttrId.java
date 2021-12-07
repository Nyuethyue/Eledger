package bhutan.eledger.adapter.out.eledger.persistence.config.transaction;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
class TransactionTypeTransactionTypeAttrId implements Serializable {

    @Column(name = "transaction_type_id")
    private Long transactionTypeId;

    @Column(name = "transaction_type_attribute_id")
    private Long transactionTypeAttributeId;

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public Long getTransactionTypeAttributeId() {
        return transactionTypeAttributeId;
    }

    public void setTransactionTypeAttributeId(Long transactionTypeAttributeId) {
        this.transactionTypeAttributeId = transactionTypeAttributeId;
    }
}
