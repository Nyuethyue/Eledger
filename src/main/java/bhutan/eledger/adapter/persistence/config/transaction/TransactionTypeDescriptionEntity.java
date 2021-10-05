package bhutan.eledger.adapter.persistence.config.transaction;

import am.iunetworks.lib.common.persistence.multilingual.entity.TranslationEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "transaction_type_description", schema = "config")
@NoArgsConstructor
class TransactionTypeDescriptionEntity extends TranslationEntity {
    @Id
    @SequenceGenerator(name = "transaction_type_description_id_seq", schema = "config", sequenceName = "transaction_type_description_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_type_description_id_seq")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id", nullable = false)
    private TransactionTypeEntity transactionType;

    public TransactionTypeDescriptionEntity(Long id, String languageCode, String value) {
        super(languageCode, value);
        this.id = id;
    }

    public TransactionTypeDescriptionEntity(Long id, String languageCode, String value, TransactionTypeEntity transactionType) {
        super(languageCode, value);
        this.id = id;
        this.transactionType = transactionType;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionTypeEntity getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionTypeEntity transactionTypeEntity) {
        this.transactionType = transactionTypeEntity;
    }

    @Override
    public boolean idSupported() {
        return true;
    }
}
